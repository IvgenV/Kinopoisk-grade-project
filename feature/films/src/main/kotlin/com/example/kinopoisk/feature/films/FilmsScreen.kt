package com.example.kinopoisk.feature.films

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.data.model.dto.FilmsByFiltersDomain


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilmsRoute(
    viewModel: FilmsViewModel,
    onItemClicked: (FilmsByFiltersDomain.ItemDomain) -> Unit,
) {

    val items: LazyPagingItems<FilmsByFiltersDomain.ItemDomain> =
        viewModel.films.collectAsLazyPagingItems()

   FilmsScreen(
       items = items,
       onItemClicked = onItemClicked
   )

}