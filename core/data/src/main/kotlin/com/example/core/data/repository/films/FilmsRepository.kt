package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.model.dto.FilmFilter
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    fun getFilmsCollections(type: String): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>>

    suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto>

    suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse>

    suspend fun getFilmPoster(id: Int, type: String): Flow<List<FilmImages.FilmsItem>>

    fun getFilmImagesPaging(filmId: Int, type: String): Flow<PagingData<FilmImages.FilmsItem>>

    suspend fun getFilmFilters(): FilmFilter

    fun getFilms(queryParams: Map<String, Any>): Flow<PagingData<FilmsByFiltersDomain.ItemDomain>>

}