package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.model.dto.FilmFilter
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsDataSource: FilmsDataSource
) : FilmsRepository {

    override fun getFilmsCollections(
        type: String
    ): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> {
        return filmsDataSource.getFilmsCollections(type)
    }

    override suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto> {
        return filmsDataSource.getFilmsPremieres(year = year, month = month)
    }

    override suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse> {
        return filmsDataSource.getFilmDetail(id)
    }

    override suspend fun getFilmPoster(id: Int, type: String): Flow<List<FilmImages.FilmsItem>>  {
        return filmsDataSource.getFilmImages(id, type)
    }

    override fun getFilmImagesPaging(
        filmId: Int,
        type: String
    ): Flow<PagingData<FilmImages.FilmsItem>> {
        return filmsDataSource.getFilmImagesPaging(
            filmId = filmId, type = type
        )
    }

    override suspend fun getFilmFilters(): FilmFilter {
        return filmsDataSource.getFilmFilters()
    }

    override fun getFilms(queryParams: Map<String, Any>): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> {
        return filmsDataSource.getFilms(queryParams)
    }

}