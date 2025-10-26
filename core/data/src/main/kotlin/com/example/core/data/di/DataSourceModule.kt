package com.example.core.data.di

import com.example.core.data.datasource.films.FilmsDataSource
import com.example.core.data.datasource.films.FilmsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    abstract fun providesFilmsDataSource(
        filmsDataSourceImpl: FilmsDataSourceImpl
    ): FilmsDataSource

}