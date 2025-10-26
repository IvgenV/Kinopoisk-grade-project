package com.example.core.data.datasource.films

import com.example.core.data.response.FilmsCollectionsResponse

interface FilmsDataSource {

    suspend fun getFilmsCollections(page: Int, type: String): FilmsCollectionsResponse

}