package com.example.core.data.model.dto



data class PremierItemDto(
    val countries: List<Country?> = emptyList(),
    val duration: Int? = null,
    val genres: List<Genre?> = emptyList(),
    val kinopoiskId: Int? = null,
    val nameEn: String? = null,
    val nameRu: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val premiereRu: String? = null,
    val year: Int? = null
) {
    data class Country(
        val country: String? = null
    )

    data class Genre(
        val genre: String? = null
    )
}
