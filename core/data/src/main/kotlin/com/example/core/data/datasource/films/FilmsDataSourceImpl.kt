package com.example.core.data.datasource.films

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.response.FilmsCollectionsResponse.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsDataSourceImpl @Inject constructor(
    private val pagingDataSourceFactory: PagingDataSourceFactory
) : FilmsDataSource {

    override fun getFilmsCollections(
        page: Int,
        type: String
    ): Flow<PagingData<Item>> {
        val pagingDataSourceImpl = pagingDataSourceFactory.create(type)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 10,
            ), pagingSourceFactory = {
               pagingDataSourceImpl
            }
        ).flow
    }

}