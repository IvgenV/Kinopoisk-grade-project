package com.example.core.data.model.response


import com.example.core.data.model.dto.FilmFilter
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmFilterDto(
    @Json(name = "countries")
    val countries: List<Country?>? = null,
    @Json(name = "genres")
    val genres: List<Genre?>? = null
) {
    @JsonClass(generateAdapter = true)
    data class Country(
        @Json(name = "country")
        val country: String? = null,
        @Json(name = "id")
        val id: Int? = null
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "genre")
        val genre: String? = null,
        @Json(name = "id")
        val id: Int? = null
    )
}

fun FilmFilterDto.toDomain() = FilmFilter(
    countries = countries?.filterNotNull()?.map { it.toDomain() }.orEmpty()
        .toCollection(ArrayList()),
    genres = genres?.filterNotNull()?.map { it.toDomain() }.orEmpty().toCollection(ArrayList()),
)

fun FilmFilterDto.Genre.toDomain() = FilmFilter.Genre(
    id = id,
    genre = genre.orEmpty(),
)

fun FilmFilterDto.Country.toDomain() = FilmFilter.Country(
    id = id,
    country = country.orEmpty()
)