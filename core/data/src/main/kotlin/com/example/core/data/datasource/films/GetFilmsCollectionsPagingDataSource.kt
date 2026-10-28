package com.example.core.data.datasource.films

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.model.response.toDomain
import com.example.core.data.network.MovieService
import com.example.kinopoisk.core.base.Constants.INITIAL_PAGE_NO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetFilmsCollectionsPagingDataSource @AssistedInject constructor(
    private val movieApi: MovieService,
    @Assisted
    private val type: String
) : PagingSource<Int, FilmsByFiltersDomain.ItemDomain>() {

    var maxPage: Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmsByFiltersDomain.ItemDomain> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_NO

            val response = movieApi.getFilmsCollections(
                page = nextPage,
                type = type
            )

            if (maxPage == null) {
                maxPage = response.totalPages
            }

            val cardList: List<FilmsByFiltersDomain.ItemDomain> =
                response.items?.mapNotNull { it?.toDomain() }.orEmpty()

            LoadResult.Page(
                data = cardList,
                prevKey = null,
                nextKey = if (nextPage == maxPage) null else nextPage.inc()
            )

        } catch (ex: Exception) {
            LoadResult.Error(Throwable(ex.message))
        }
    }


    override fun getRefreshKey(state: PagingState<Int, FilmsByFiltersDomain.ItemDomain>): Int? {
        return state.anchorPosition
    }
}

@AssistedFactory
interface GetFilmsCollectionsPagingDataSourceFactory {
    fun create(
        type: String
    ): GetFilmsCollectionsPagingDataSource
}