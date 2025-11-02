package com.example.core.data.repository.films

import androidx.paging.PagingData
import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.response.FilmsCollectionsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val filmsDataSource: FilmsDataSource
): FilmsRepository {

    override fun getFilmsCollections(page: Int, type: String): Flow<PagingData<FilmsCollectionsResponse.Item>> {
       return filmsDataSource.getFilmsCollections(page, type)
    }

}