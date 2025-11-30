package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.PostersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsDataSource: FilmsDataSource
) : FilmsRepository {

    override fun getFilmsCollections(
        page: Int,
        type: String
    ): Flow<PagingData<FilmsCollectionsDto.Item>> {
        return filmsDataSource.getFilmsCollections(page, type)
    }

    override suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto> {
        return filmsDataSource.getFilmsPremieres(year = year, month = month)
    }

    override suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse> {
        return filmsDataSource.getFilmDetail(id)
    }

    override suspend fun getFilmPoster(id: Int, type: String): Flow<PostersResponse> {
        return filmsDataSource.getFilmPoster(id, type)
    }

}