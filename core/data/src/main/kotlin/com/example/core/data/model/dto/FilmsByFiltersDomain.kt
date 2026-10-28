package com.example.core.data.model.dto

import androidx.compose.runtime.Immutable

@Immutable
data class FilmsByFiltersDomain(
    val itemDomains: List<ItemDomain?> = emptyList(),
) {
    @Immutable
    class ItemDomain(
        val countries: List<CountryDomain?> = emptyList(),
        val genreDomains: List<GenreDomain?> = emptyList(),
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
        @Immutable
        class CountryDomain(
            val country: String? = null
        )

        @Immutable
        class GenreDomain(
            val genre: String? = null
        )
    }
}