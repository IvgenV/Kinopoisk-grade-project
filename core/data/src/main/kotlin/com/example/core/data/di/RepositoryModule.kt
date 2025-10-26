package com.example.core.data.di

import com.example.core.data.repository.films.FilmsRepository
import com.example.core.data.repository.films.FilmsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    abstract fun providesFilmsRepository(
        filmsRepository: FilmsRepositoryImpl
    ): FilmsRepository

}