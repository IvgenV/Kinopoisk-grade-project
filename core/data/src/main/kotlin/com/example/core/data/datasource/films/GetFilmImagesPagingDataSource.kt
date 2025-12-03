package com.example.core.data.datasource.films

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.response.toDomain
import com.example.core.data.network.MovieService
import com.example.kinopoisk.core.base.Constants.INITIAL_PAGE_NO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class GetFilmImagesPagingDataSource @AssistedInject constructor(
    private val movieApi: MovieService,
    @Assisted
    private val type: String,
    @Assisted
    private val filmId: Int,
) : PagingSource<Int, FilmImages.FilmsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmImages.FilmsItem> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_NO
            val maxPage: Int

            val response = movieApi.getFilmImages(
                page = nextPage,
                type = type,
                id = filmId
            )

            maxPage = response.totalPages ?: 0
            val filmImages: List<FilmImages.FilmsItem> =
                response.items?.mapNotNull { it?.toDomain() }.orEmpty()
            LoadResult.Page(
                data = filmImages,
                prevKey = null,
                nextKey = if (nextPage == maxPage) null else nextPage.inc()
            )

        } catch (ex: Exception) {
            LoadResult.Error(Throwable(ex.message))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmImages.FilmsItem>): Int? {
        return state.anchorPosition
    }

}


@AssistedFactory
interface GetFilmImagesPagingDataSourceFactory {
    fun create(
        type: String,
        filmId: Int,
    ): GetFilmImagesPagingDataSource
}