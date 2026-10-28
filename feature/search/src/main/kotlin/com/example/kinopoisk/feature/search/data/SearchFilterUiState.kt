package com.example.kinopoisk.feature.search.data

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import com.example.core.data.model.dto.FilmFilter.Country
import com.example.core.data.model.dto.FilmFilter.Genre
import com.example.kinopoisk.feature.search.data.ProductType.*
import com.example.kinopoisk.feature.search.data.SearchFilterSort.*
import kotlinx.serialization.Serializable


@Serializable
data class SearchFilterUiState(
    val countries: ArrayList<Country> = arrayListOf(),
    val genres: ArrayList<Genre> = arrayListOf(),
    val selectedCountry: String = "",
    val selectedGenre: String = "",
    val selectedYear: String = "",
    val sortedBy: SearchFilterSort = RATING,
    val productType: ProductType = ALL,
    val isHighRating: Boolean = false,
)

fun SearchFilterUiState.toQueryPrams() = mapOf<String, Any>().apply {
    if (selectedCountry.isNotBlank()) {
        "countries" to selectedCountry
    }
    if (selectedGenre.isNotBlank()) {
        "genres" to selectedGenre
    }
    if (selectedYear.isNotBlank()) {
        "yearFrom" to selectedYear
        "yearTo" to selectedYear
    }
    "order" to sortedBy.sortName
    "type" to productType.name
    if (isHighRating) {
        "ratingFrom" to 7
    }
}

enum class SearchFilterSort(val sortName: String) {
    RATING("Рейтингу"), NUM_VOTE("Популярности"), YEAR("Дате")
}

enum class ProductType(val sortName: String) {
    ALL("Все"), FILM("Фильмы"), TV_SHOW("Сериалы")
}


val UserSaver = Saver<SearchFilterUiState, Bundle>(
    save = { searchFilterUiState ->
        Bundle().apply {
            putSerializable("countries", searchFilterUiState.countries)
            putSerializable("genres", searchFilterUiState.genres)
            putString("selectedCountry", searchFilterUiState.selectedCountry)
            putString("selectedGenre", searchFilterUiState.selectedGenre)
            putString("selectedYear", searchFilterUiState.selectedYear)
            putSerializable("sortedBy", searchFilterUiState.sortedBy)
            putSerializable("productType", searchFilterUiState.productType)
            putBoolean("isHighRating", searchFilterUiState.isHighRating)
        }
    },
    restore = { bundle ->
        SearchFilterUiState(
            countries = bundle.getSerializable("countries") as? ArrayList<Country> ?: arrayListOf(),
            genres = bundle.getSerializable("genres") as? ArrayList<Genre> ?: arrayListOf(),
            selectedCountry = bundle.getString("selectedCountry", ""),
            selectedGenre = bundle.getString("selectedGenre", ""),
            selectedYear = bundle.getString("selectedYear", ""),
            sortedBy = bundle.getSerializable("selectedYear") as? SearchFilterSort ?: RATING,
            productType = bundle.getSerializable("productType") as? ProductType ?: ALL,
            isHighRating = bundle.getBoolean("isHighRating"),
        )
    }
)