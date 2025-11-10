package com.example.core.data.model.response


import com.example.core.data.model.dto.FilmsCollectionsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmsCollectionsResponse(
    @Json(name = "items")
    val items: List<Item?>? = null,
    @Json(name = "total")
    val total: Int? = null,
    @Json(name = "totalPages")
    val totalPages: Int? = null
) {
    @JsonClass(generateAdapter = true)
    class Item(
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
        class Country(
            @Json(name = "country")
            val country: String? = null
        )

        @JsonClass(generateAdapter = true)
        class Genre(
            @Json(name = "genre")
            val genre: String? = null
        )
    }
}

fun FilmsCollectionsResponse.toTdo() = FilmsCollectionsDto(
    items = items?.map { it?.toTdo() } ?: emptyList(),
    total = total ?: 0,
    totalPages = totalPages ?: 0
)

fun FilmsCollectionsResponse.Item.toTdo() = FilmsCollectionsDto.Item(
    countries = countries?.map { it?.toDto() } ?: emptyList(),
    genres = genres?.map { it?.toDto() } ?: emptyList(),
    kinopoiskId = kinopoiskId,
    nameEn = nameEn,
    nameOriginal = nameOriginal,
    nameRu = nameRu,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    ratingImbd = ratingImbd,
    ratingKinopoisk = ratingKinopoisk,
    type = type,
    year = year
)

fun FilmsCollectionsResponse.Item.Country.toDto() = FilmsCollectionsDto.Item.Country(
    country = country
)

fun FilmsCollectionsResponse.Item.Genre.toDto() = FilmsCollectionsDto.Item.Genre(
    genre = genre
)