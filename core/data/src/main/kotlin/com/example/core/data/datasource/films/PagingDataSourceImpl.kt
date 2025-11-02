package com.example.core.data.datasource.films

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.network.MovieService
import com.example.core.data.response.FilmsCollectionsResponse.Item
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

const val INITIAL_PAGE_NO = 1

class PagingDataSourceImpl @AssistedInject constructor(
    private val movieApi: MovieService,
    @Assisted
    private val type: String
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_NO
            val maxPage: Int

            movieApi.getFilmsCollections(
                page = nextPage,
                type = type
            ).let {
                maxPage = it.totalPages ?: 0
                val cardList: List<Item> = it.items?.filterNotNull() ?: emptyList()
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


    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }
}

@AssistedFactory
interface PagingDataSourceFactory {
    fun create(
        type: String
    ): PagingDataSourceImpl
}