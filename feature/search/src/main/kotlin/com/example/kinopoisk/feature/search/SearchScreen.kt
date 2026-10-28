package com.example.kinopoisk.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.feature.search.components.SearchFilterMainScreen
import com.example.kinopoisk.feature.search.components.SearchFilterScreen
import com.example.kinopoisk.feature.search.data.SearchFilterUiState

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    toFilterCLick: (SearchFilterUiState) -> Unit
) {

    val films = viewModel.st.collectAsLazyPagingItems()

    var textRequest by rememberSaveable {
        mutableStateOf("")
    }

    SearchFilterMainScreen(
        request = textRequest,
        onTextChange = {
            textRequest = it
        },
        filterClicked = {
            toFilterCLick.invoke(viewModel.searchFilterUiState)
        },
        films = films
    )

}