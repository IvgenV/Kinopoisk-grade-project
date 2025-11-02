package com.example.core.data.datasource.films

import androidx.paging.PagingData
import com.example.core.data.response.FilmsCollectionsResponse
import kotlinx.coroutines.flow.Flow

interface FilmsDataSource {

    fun getFilmsCollections(page: Int, type: String): Flow<PagingData<FilmsCollectionsResponse.Item>>

}