package com.example.core.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmsCollectionsResponse(
    @Json(name = "items")
    val items: List<Item?>? = null,
    @Json(name = "total")
    val total: Int? = null,
    @Json(name = "totalPages")
    val totalPages: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "countries")
        val countries: List<Country?>? = null,
        @Json(name = "genres")
        val genres: List<Genre?>? = null,
        @Json(name = "kinopoiskId")
        val kinopoiskId: Int? = null,
        @Json(name = "nameEn")
        val nameEn: String? = null,
        @Json(name = "nameOriginal")
        val nameOriginal: String? = null,
        @Json(name = "nameRu")
        val nameRu: String? = null,
        @Json(name = "posterUrl")
        val posterUrl: String? = null,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String? = null,
        @Json(name = "ratingImbd")
        val ratingImbd: Double? = null,
        @Json(name = "ratingKinopoisk")
        val ratingKinopoisk: Double? = null,
        @Json(name = "type")
        val type: String? = null,
        @Json(name = "year")
        val year: String? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Country(
            @Json(name = "country")
            val country: String? = null
        )

        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "genre")
            val genre: String? = null
        )
    }
}