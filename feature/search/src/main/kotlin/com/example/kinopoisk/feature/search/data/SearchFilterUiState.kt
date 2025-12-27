package com.example.kinopoisk.feature.search.data

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import com.example.core.data.model.dto.FilmFilter.Country
import com.example.core.data.model.dto.FilmFilter.Genre
import kotlinx.serialization.Serializable


@Serializable
data class SearchFilterUiState(
    val countries: ArrayList<Country>? = null,
    val genres: ArrayList<Genre>? = null,
    val selectedCountry: String? = null,
    val selectedGenre: String? = null,
    val selectedYear: String? = null,
    val sortedBy: SearchFilterSort? = null,
    val productType: ProductType? = null,
    val isHighRating: Boolean = false,
)

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
            countries = bundle.getSerializable("countries") as? ArrayList<Country>,
            genres = bundle.getSerializable("genres") as? ArrayList<Genre>,
            selectedCountry = bundle.getString("selectedCountry"),
            selectedGenre = bundle.getString("selectedGenre"),
            selectedYear = bundle.getString("selectedYear"),
            sortedBy = bundle.getSerializable("selectedYear") as? SearchFilterSort,
            productType = bundle.getSerializable("productType") as? ProductType,
            isHighRating = bundle.getBoolean("isHighRating"),
        )
    }
)