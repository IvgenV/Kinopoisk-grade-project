package com.example.core.data.datasource.films

import androidx.paging.PagingData
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.dto.PremierItemDto
import kotlinx.coroutines.flow.Flow

interface FilmsDataSource {

    fun getFilmsCollections(page: Int, type: String): Flow<PagingData<FilmsCollectionsDto.Item>>

    suspend fun getFilmsPremieres(year: Int, month: String): List<PremierItemDto>


}