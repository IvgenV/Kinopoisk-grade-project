package com.example.kinopoisk.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.model.dto.FilmFilter
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.repository.films.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat

@HiltViewModel
class SearchViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    private val filmFilter: FilmFilter = FilmFilter()

    private val queryParams = mutableMapOf<String, Any>(
        "order" to "RATING",
        "type" to "FILM",
        "ratingFrom" to 0,
        "ratingTo" to 10,
        "yearFrom" to 1000,
        "yearTo" to 3000,
    )

    val films: Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = filmsRepository.getFilmFilters()
        .flatMapConcat { response ->
            filmFilter.genres = response.genres
            filmFilter.countries = response.countries
            filmFilter.countries.firstOrNull()?.let {
                queryParams["countries"] = it
            }
            filmFilter.genres.firstOrNull()?.let {
                queryParams["genres"] = it
            }
            filmsRepository.getFilms(queryParams)
        }.catch {

        }.cachedIn(viewModelScope)

}