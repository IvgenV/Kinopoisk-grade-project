package com.example.core.data.model.dto

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.saveable.Saver
import com.example.core.data.model.dto.FilmFilter.Country
import com.example.core.data.model.dto.FilmFilter.Genre
import com.example.core.data.model.dto.SearchDetailFilter.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

class FilmFilter(
    var countries: ArrayList<Country> = arrayListOf(),
    var genres: ArrayList<Genre> = arrayListOf(),
) {
    @Serializable
    @Parcelize
    data class Country(
        val country: String = "null",
        val id: Int? = null
    ) : Parcelable

    @Serializable
    @Parcelize
    data class Genre(
        val genre: String = "null",
        val id: Int? = null
    ) : Parcelable

}

sealed class SearchDetailFilter : Parcelable {
    @Parcelize
    data class CountryFilter(val countries: ArrayList<Country>) : SearchDetailFilter()

    @Parcelize
    data class GenreFilter(val genres: ArrayList<Genre>) : SearchDetailFilter()

    @Parcelize
    data object YearsFilter : SearchDetailFilter(), Parcelable
}

fun SearchDetailFilter.getTitle() =
    when (this) {
        is CountryFilter -> "Страны"
        is GenreFilter -> "Жанры"
        YearsFilter -> "Годы"
    }


val SearchDetailFilterSaver = Saver<SearchDetailFilter, Bundle>(
    save = { searchFilterUiState ->
        Bundle().apply {
            putParcelable("searchFilterUiState", searchFilterUiState)
        }
    },
    restore = { bundle ->
        bundle.getParcelable("searchFilterUiState")
    }
)