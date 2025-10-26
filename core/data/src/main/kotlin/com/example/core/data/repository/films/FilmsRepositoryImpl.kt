package com.example.core.data.repository.films

import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.response.FilmsCollectionsResponse
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsDataSource: FilmsDataSource
): FilmsRepository {

    override suspend fun getFilmsCollections(page: Int, type: String): FilmsCollectionsResponse {
       return filmsDataSource.getFilmsCollections(page, type)
    }

}