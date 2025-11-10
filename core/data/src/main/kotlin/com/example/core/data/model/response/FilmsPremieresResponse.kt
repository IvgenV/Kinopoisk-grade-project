package com.example.core.data.model.response


import com.example.core.data.model.dto.PremierItemDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmsPremieresResponse(
    @Json(name = "items")
    val items: List<Item?>? = null,
    @Json(name = "total")
    val total: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "countries")
        val countries: List<Country?>? = null,
        @Json(name = "duration")
        val duration: Int? = null,
        @Json(name = "genres")
        val genres: List<Genre?>? = null,
        @Json(name = "kinopoiskId")
        val kinopoiskId: Int? = null,
        @Json(name = "nameEn")
        val nameEn: String? = null,
        @Json(name = "nameRu")
        val nameRu: String? = null,
        @Json(name = "posterUrl")
        val posterUrl: String? = null,
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String? = null,
        @Json(name = "premiereRu")
        val premiereRu: String? = null,
        @Json(name = "year")
        val year: Int? = null
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

fun FilmsPremieresResponse.toDto(): List<PremierItemDto> = items?.mapNotNull { it?.toDto() } ?: emptyList()

fun FilmsPremieresResponse.Item.toDto() = PremierItemDto(
    countries = countries?.map { it?.toDto() } ?: emptyList(),
    duration = duration,
    genres = genres?.map { it?.toDto() } ?: emptyList(),
    kinopoiskId = kinopoiskId,
    nameEn = nameEn,
    nameRu = nameRu,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    premiereRu = premiereRu,
    year = year
)

fun FilmsPremieresResponse.Item.Country.toDto() = PremierItemDto.Country(
    country = country
)

fun FilmsPremieresResponse.Item.Genre.toDto() = PremierItemDto.Genre(
    genre = genre
)