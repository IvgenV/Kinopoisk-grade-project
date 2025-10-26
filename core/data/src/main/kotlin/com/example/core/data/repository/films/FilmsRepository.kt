package com.example.core.data.repository.films

import com.example.core.data.response.FilmsCollectionsResponse

interface FilmsRepository {

    suspend fun getFilmsCollections(page: Int, type: String): FilmsCollectionsResponse

}