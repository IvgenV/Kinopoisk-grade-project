package com.example.kinopoisk.feature.search.filter

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.core.data.model.dto.SearchDetailFilter
import com.example.core.data.model.dto.SearchDetailFilter.CountryFilter
import com.example.core.data.model.dto.SearchDetailFilter.GenreFilter
import com.example.core.data.model.dto.SearchDetailFilter.YearsFilter
import com.example.core.data.model.dto.SearchDetailFilterSaver
import com.example.kinopoisk.feature.search.SearchViewModel
import com.example.kinopoisk.feature.search.components.SearchFilterScreen
import com.example.kinopoisk.feature.search.data.SearchFilterUiState
import com.example.kinopoisk.feature.search.data.UserSaver
import com.example.kinopoisk.feature.search.filter.TYPE_OF_SHOWED_SCREEN.DETAIL_FILTER
import com.example.kinopoisk.feature.search.filter.TYPE_OF_SHOWED_SCREEN.MAIN_FILTER
import com.example.kinopoisk.feature.search.filterDetail.FilterDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterRoute(
    viewModel: SearchViewModel,
    filtersUiState: SearchFilterUiState,
    applyFilter: (SearchFilterUiState) -> Unit,
) {

    var localFiltersUiState by rememberSaveable(
        stateSaver = UserSaver
    ) {
        mutableStateOf(filtersUiState.copy())
    }

    var showedScreen by rememberSaveable {
        mutableStateOf(MAIN_FILTER)
    }

    var selectedDetailsFilterScreen: SearchDetailFilter by rememberSaveable(
        stateSaver = SearchDetailFilterSaver
    ) {
        mutableStateOf(CountryFilter(countries = localFiltersUiState.countries ?: arrayListOf()))
    }

    when (showedScreen) {
        MAIN_FILTER -> {
            SearchFilterScreen(
                localFiltersUiState,
                applyFilter = {
                    applyFilter(localFiltersUiState)
                },
                filterDetailClick = { searchDetailFilter ->
                    selectedDetailsFilterScreen = when (searchDetailFilter) {
                        is CountryFilter -> {
                            searchDetailFilter
                        }

                        is GenreFilter -> {
                            searchDetailFilter
                        }

                        YearsFilter -> {
                            searchDetailFilter
                        }
                    }
                    showedScreen = DETAIL_FILTER
                },
                sortClick = { sortType ->
                    localFiltersUiState = localFiltersUiState.copy(
                        sortedBy = sortType
                    )
                },
                typeClick = { type ->
                    localFiltersUiState = localFiltersUiState.copy(
                        productType = type
                    )
                },
                highRatingCheck = { isHighRating ->
                    localFiltersUiState = localFiltersUiState.copy(
                        isHighRating = isHighRating
                    )
                },
                restUiState = {
                    localFiltersUiState = filtersUiState
                }
            )
        }

        DETAIL_FILTER -> {
            FilterDetailScreen(
                selectedDetailsFilterScreen,
                when (selectedDetailsFilterScreen) {
                    is CountryFilter -> {
                        localFiltersUiState.selectedCountry
                    }

                    is GenreFilter -> {
                        localFiltersUiState.selectedGenre
                    }

                    YearsFilter -> {
                        localFiltersUiState.selectedYear
                    }
                },
                onClick = { selectedItem ->
                    when (selectedDetailsFilterScreen) {
                        is CountryFilter -> {
                            localFiltersUiState = localFiltersUiState.copy(
                                selectedCountry = selectedItem
                            )
                        }

                        is GenreFilter -> {
                            localFiltersUiState = localFiltersUiState.copy(
                                selectedGenre = selectedItem
                            )
                        }

                        YearsFilter -> {
                            localFiltersUiState = localFiltersUiState.copy(
                                selectedYear = selectedItem
                            )
                        }
                    }
                    showedScreen = MAIN_FILTER
                },
                onBack = {
                    showedScreen = MAIN_FILTER
                }
            )
        }
    }

}

enum class TYPE_OF_SHOWED_SCREEN {
    MAIN_FILTER, DETAIL_FILTER
}