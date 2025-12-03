package com.example.core.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmDetailResponse(
    @Json(name = "completed")
    val completed: Boolean? = null,
    @Json(name = "countries")
    val countries: List<Country?>? = null,
    @Json(name = "coverUrl")
    val coverUrl: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "editorAnnotation")
    val editorAnnotation: String? = null,
    @Json(name = "endYear")
    val endYear: Int? = null,
    @Json(name = "filmLength")
    val filmLength: Int? = null,
    @Json(name = "genres")
    val genres: List<Genre?>? = null,
    @Json(name = "has3D")
    val has3D: Boolean? = null,
    @Json(name = "hasImax")
    val hasImax: Boolean? = null,
    @Json(name = "imdbId")
    val imdbId: String? = null,
    @Json(name = "isTicketsAvailable")
    val isTicketsAvailable: Boolean? = null,
    @Json(name = "kinopoiskHDId")
    val kinopoiskHDId: String? = null,
    @Json(name = "kinopoiskId")
    val kinopoiskId: Int? = null,
    @Json(name = "lastSync")
    val lastSync: String? = null,
    @Json(name = "logoUrl")
    val logoUrl: String? = null,
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
    @Json(name = "productionStatus")
    val productionStatus: String? = null,
    @Json(name = "ratingAgeLimits")
    val ratingAgeLimits: String? = null,
    @Json(name = "ratingAwait")
    val ratingAwait: Double? = null,
    @Json(name = "ratingAwaitCount")
    val ratingAwaitCount: Int? = null,
    @Json(name = "ratingFilmCritics")
    val ratingFilmCritics: Double? = null,
    @Json(name = "ratingFilmCriticsVoteCount")
    val ratingFilmCriticsVoteCount: Int? = null,
    @Json(name = "ratingGoodReview")
    val ratingGoodReview: Double? = null,
    @Json(name = "ratingGoodReviewVoteCount")
    val ratingGoodReviewVoteCount: Int? = null,
    @Json(name = "ratingImdb")
    val ratingImdb: Double? = null,
    @Json(name = "ratingImdbVoteCount")
    val ratingImdbVoteCount: Int? = null,
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Double? = null,
    @Json(name = "ratingKinopoiskVoteCount")
    val ratingKinopoiskVoteCount: Int? = null,
    @Json(name = "ratingMpaa")
    val ratingMpaa: String? = null,
    @Json(name = "ratingRfCritics")
    val ratingRfCritics: Double? = null,
    @Json(name = "ratingRfCriticsVoteCount")
    val ratingRfCriticsVoteCount: Int? = null,
    @Json(name = "reviewsCount")
    val reviewsCount: Int? = null,
    @Json(name = "serial")
    val serial: Boolean? = null,
    @Json(name = "shortDescription")
    val shortDescription: String? = null,
    @Json(name = "shortFilm")
    val shortFilm: Boolean? = null,
    @Json(name = "slogan")
    val slogan: String? = null,
    @Json(name = "startYear")
    val startYear: Int? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "webUrl")
    val webUrl: String? = null,
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