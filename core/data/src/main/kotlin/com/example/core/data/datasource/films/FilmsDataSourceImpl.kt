package com.example.core.data.datasource.films

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.model.dto.FilmFilter
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.toDomain
import com.example.core.data.network.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FilmsDataSourceImpl @Inject constructor(
    private val getFilmsCollectionsPagingDataSourceFactory: GetFilmsCollectionsPagingDataSourceFactory,
    private val getFilmsPagingDataSourceFactory: GetFilmsPagingDataSourceFactory,
    private val getFilmImagesPagingDataSource: GetFilmImagesPagingDataSourceFactory,
    private val movieService: MovieService
) : FilmsDataSource {

    override fun getFilmsCollections(
        type: String
    ): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> {
        val pagingDataSourceImpl = getFilmsCollectionsPagingDataSourceFactory.create(type)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 10,
            ), pagingSourceFactory = {
                pagingDataSourceImpl
            }
        ).flow
    }

    override suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto> {
        return movieService.getPremieres(year = year, month = month).toDomain()
    }

    override suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse> {
        return flow {
            val response = movieService.getFilmDetail(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFilmImages(id: Int, type: String): Flow<List<FilmImages.FilmsItem>> {
        return flow {
            val response =
                movieService.getFilmImages(id, type = type).items?.mapNotNull { it?.toDomain() }
                    .orEmpty()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override fun getFilmImagesPaging(
        filmId: Int,
        type: String
    ): Flow<PagingData<FilmImages.FilmsItem>> {
        val pagingDataSourceImpl = getFilmImagesPagingDataSource.create(type, filmId)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 10,
            ), pagingSourceFactory = {
                pagingDataSourceImpl
            }
        ).flow
    }

    override fun getFilmFilters(): Flow<FilmFilter> {
        return flow {
            val response =
                movieService.getFilmsFilters().toDomain()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override fun getFilms(
        queryParams: Map<String, Any>
    ): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> {
        val pagingDataSourceImpl = getFilmsPagingDataSourceFactory.create(queryParams)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 10,
            ), pagingSourceFactory = {
                pagingDataSourceImpl
            }
        ).flow
    }

}