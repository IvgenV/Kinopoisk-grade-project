package com.example.kinopoisk.feature.search

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.data.model.dto.FilmsByFiltersDomain

@Composable
fun SearchRoute(
    viewModel: SearchViewModel
) {

    val films = viewModel.films.collectAsLazyPagingItems()

    SearchScreen(films)

}

@Composable
private fun SearchScreen(
    films: LazyPagingItems<FilmsByFiltersDomain.ItemDomain>
) {



}