package com.example.kinopoisk.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.model.FiltersForSearch
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.repository.films.FilmsRepository
import com.example.kinopoisk.feature.search.data.SearchFilterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    var searchFilterUiState = SearchFilterUiState()

    private val queryParams = mutableMapOf<String, Any>(
        "order" to "RATING",
        "type" to "FILM",
        "ratingFrom" to 0,
        "ratingTo" to 10,
        "yearFrom" to 1000,
        "yearTo" to 3000,
    )

//    init {
//        getFilmFilters()
//    }

//    fun getFilmFilters() = viewModelScope.launch(Dispatchers.IO) {
//        runCatching {
//            filmsRepository.getFilmFilters()
//        }.onSuccess { response ->
//            filtersForSearch = FiltersForSearch(
//                genres = response.genres.map { it.genre },
//                countries = response.countries.map { it.country }
//            )
//        }.onFailure {
//
//        }
//    }
//
//    fun getFilms() =
//         filmsRepository.getFilms(queryParams).catch {
//            it
//        }.cachedIn(viewModelScope)

    val films: Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = filmsRepository.getFilmFilters()
        .flatMapLatest { response ->

            searchFilterUiState = SearchFilterUiState(
                genres = response.genres,
                countries = response.countries
            )

            searchFilterUiState.countries?.firstOrNull()?.id?.let {
                queryParams["countries"] = it
            }

            searchFilterUiState.genres?.firstOrNull()?.id?.let {
                queryParams["genres"] = it
            }
            filmsRepository.getFilms(queryParams)
        }.catch {
            val st = it
            it
        }.cachedIn(viewModelScope)

}

