package com.example.core.data.model.dto

class FilmFilter(
    var countries: List<Country> = emptyList(),
    var genres: List<Genre> = emptyList()
) {
    data class Country(
        val country: String = "null",
        val id: Int? = null
    )

    data class Genre(
        val genre: String = "null",
        val id: Int? = null
    )

}