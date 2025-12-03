package com.example.core.data.network

import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.FilmsCollectionsResponse
import com.example.core.data.model.response.FilmsPremieresResponse
import com.example.core.data.model.response.ImagesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmDetail(
        @Path("id") id: Int
    ): FilmDetailResponse

    @GET("/api/v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("page") page: Int = 1,
        @Query("type") type: String,
    ): ImagesResponseDto

}