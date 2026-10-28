package com.example.kinopoisk.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.repository.films.FilmsRepository
import com.example.kinopoisk.feature.search.data.SearchFilterUiState
import com.example.kinopoisk.feature.search.data.toQueryPrams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    var searchFilterUiState = SearchFilterUiState()
    private val _searchFilterUiState = MutableStateFlow(SearchFilterUiState())

    fun setUiFilterState(searchFilterUiState: SearchFilterUiState) {
        _searchFilterUiState.value = searchFilterUiState
        this.searchFilterUiState = searchFilterUiState
    }

    private val queryParams = mutableMapOf<String, Any>(
        "order" to "RATING",
        "type" to "FILM",
        "ratingFrom" to 0,
        "ratingTo" to 10,
        "yearFrom" to 1000,
        "yearTo" to 3000,
    )

    init {
        viewModelScope.launch {
            runCatching {
                filmsRepository.getFilmFilters()
            }.onSuccess { response ->
                _searchFilterUiState.value = _searchFilterUiState.value.copy(
                    genres = response.genres,
                    countries = response.countries
                )
            }.onFailure {

            }
        }

    }

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

    val st = _searchFilterUiState.flatMapLatest { _ ->
        filmsRepository.getFilms(_searchFilterUiState.value.toQueryPrams())
    }.catch {
        val st = it
        it
    }.cachedIn(viewModelScope)

//    val films: Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = filmsRepository.getFilmFilters()
//        .flatMapLatest { response ->
//
//            searchFilterUiState = SearchFilterUiState(
//                genres = response.genres,
//                countries = response.countries
//            )
//
//            searchFilterUiState.countries?.firstOrNull()?.id?.let {
//                queryParams["countries"] = it
//            }
//
//            searchFilterUiState.genres?.firstOrNull()?.id?.let {
//                queryParams["genres"] = it
//            }
//            filmsRepository.getFilms(queryParams)
//        }.catch {
//            val st = it
//            it
//        }.cachedIn(viewModelScope)

}

