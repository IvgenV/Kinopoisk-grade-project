package com.example.core.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.response.toTdo
import com.example.core.data.network.MovieService
import com.example.kinopoisk.core.base.Constants.INITIAL_PAGE_NO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetFilmsPagingFataSource @AssistedInject constructor(
    private val movieApi: MovieService,
    @Assisted
    private val type: String
) : PagingSource<Int, FilmsCollectionsDto.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmsCollectionsDto.Item> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_NO
            val maxPage: Int

            movieApi.getFilmsCollections(
                page = nextPage,
                type = type
            ).toTdo().let {
                maxPage = it.totalPages
                val cardList: List<FilmsCollectionsDto.Item> =
                    it.items.filterNotNull()
                LoadResult.Page(
                    data = cardList,
                    prevKey = null,
                    nextKey = if (nextPage == maxPage) null else nextPage.inc()
                )
            }

        } catch (ex: Exception) {
            LoadResult.Error(Throwable(ex.message))
        }
    }


    override fun getRefreshKey(state: PagingState<Int, FilmsCollectionsDto.Item>): Int? {
        return state.anchorPosition
    }
}

@AssistedFactory
interface PagingDataSourceFactory {
    fun create(
        type: String
    ): GetFilmsPagingFataSource
}