package com.example.core.data.datasource.films

import com.example.core.data.network.MovieService
import com.example.core.data.response.FilmsCollectionsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsDataSourceImpl @Inject constructor(
    private val movieApi: MovieService,
): FilmsDataSource  {

    override suspend fun getFilmsCollections(page: Int, type: String): FilmsCollectionsResponse {
        return movieApi.getFilmsCollections(page, type)
    }

}