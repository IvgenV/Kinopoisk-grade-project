package com.example.core.data.model.response


import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FilmsByFiltersDto(
    @Json(name = "items")
    val items: List<Item?>? = null,
): BasePagingResponse() {
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

fun FilmsByFiltersDto.Item.toDomain() = FilmsByFiltersDomain.ItemDomain(
    countries = countries?.map { it?.toDomain() } ?: emptyList(),
    genreDomains = genres?.map { it?.toDomain() } ?: emptyList(),
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

fun FilmsByFiltersDto.Item.Country.toDomain() = FilmsByFiltersDomain.ItemDomain.CountryDomain(
    country = country
)

fun FilmsByFiltersDto.Item.Genre.toDomain() = FilmsByFiltersDomain.ItemDomain.GenreDomain(
    genre = genre
)