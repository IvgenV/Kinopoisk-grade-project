package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.dto.PremierItemDto
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

}