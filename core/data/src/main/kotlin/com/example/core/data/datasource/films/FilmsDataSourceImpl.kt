package com.example.core.data.datasource.films

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.datasource.paging.PagingDataSourceFactory
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.PostersResponse
import com.example.core.data.model.response.toDto
import com.example.core.data.network.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject


class FilmsDataSourceImpl @Inject constructor(
    private val pagingDataSourceFactory: PagingDataSourceFactory,
    private val movieService: MovieService
) : FilmsDataSource {

    override fun getFilmsCollections(
        page: Int,
        type: String
    ): Flow<PagingData<FilmsCollectionsDto.Item>> {
        val pagingDataSourceImpl = pagingDataSourceFactory.create(type)
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
        return movieService.getPremieres(year = year, month = month).toDto()
    }

    override suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse> {
        return flow {
            val response = movieService.getFilmDetail(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFilmPoster(id: Int, type: String): Flow<PostersResponse> {
        return flow {
            val response = movieService.getFilmImages(id, type)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

}