package com.example.core.data.network

import com.example.core.data.model.response.FilmsCollectionsResponse
import com.example.core.data.model.response.FilmsPremieresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/api/v2.2/films/collections")
    suspend fun getFilmsCollections(
        @Query("page") page: Int,
        @Query("type") type: String,
    ): FilmsCollectionsResponse

    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String,
    ): FilmsPremieresResponse

}