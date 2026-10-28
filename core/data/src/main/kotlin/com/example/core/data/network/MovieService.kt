package com.example.core.data.network

import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.FilmFilterDto
import com.example.core.data.model.response.FilmsByFiltersDto
import com.example.core.data.model.response.FilmsPremieresResponse
import com.example.core.data.model.response.ImagesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieService {

    @GET("/api/v2.2/films/collections")
    suspend fun getFilmsCollections(
        @Query("page") page: Int,
        @Query("type") type: String,
    ): FilmsByFiltersDto

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

    @GET("/api/v2.2/films/filters")
    suspend fun getFilmsFilters(): FilmFilterDto

    @GET("/api/v2.2/films")
    @JvmSuppressWildcards
    suspend fun films(
        @QueryMap params: Map<String, Any>
    ): FilmsByFiltersDto

}