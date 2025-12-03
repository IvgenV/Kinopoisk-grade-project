package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.ImagesResponseDto
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {

    fun getFilmsCollections(type: String): Flow<PagingData<FilmsCollectionsDto.Item>>

    suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto>

    suspend fun getFilmDetail(id: Int): Flow<FilmDetailResponse>

    suspend fun getFilmPoster(id: Int, type: String): Flow<List<FilmImages.FilmsItem>>

    fun getFilmImagesPaging(filmId: Int, type: String): Flow<PagingData<FilmImages.FilmsItem>>

}