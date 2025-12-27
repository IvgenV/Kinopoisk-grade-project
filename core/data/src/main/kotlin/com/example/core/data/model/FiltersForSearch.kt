package com.example.core.data.model

import com.example.core.data.model.dto.FilmFilter.Country
import com.example.core.data.model.dto.FilmFilter.Genre
import kotlinx.serialization.Serializable

@Serializable
data class FiltersForSearch(
    val countries: List<Country>? = null,
    val genres: List<Genre>? = null,
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
    val keyword: String? = null,
    val type: String? = null,
)
