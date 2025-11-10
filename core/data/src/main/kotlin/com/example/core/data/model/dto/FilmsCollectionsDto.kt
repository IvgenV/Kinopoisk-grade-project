package com.example.core.data.model.dto

data class FilmsCollectionsDto(
    val items: List<Item?> = emptyList(),
    val total: Int = 0,
    val totalPages: Int = 0
) {
    class Item(
        val countries: List<Country?> = emptyList(),
        val genres: List<Genre?> = emptyList(),
        val kinopoiskId: Int? = null,
        val nameEn: String? = null,
        val nameOriginal: String? = null,
        val nameRu: String? = null,
        val posterUrl: String? = null,
        val posterUrlPreview: String? = null,
        val ratingImbd: Double? = null,
        val ratingKinopoisk: Double? = null,
        val type: String? = null,
        val year: String? = null
    ) {
        class Country(
            val country: String? = null
        )

        class Genre(
            val genre: String? = null
        )
    }
}