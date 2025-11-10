package com.example.kinopoisk.feature.films

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.model.dto.FilmsCollectionsDto
import com.example.core.data.model.response.FilmsCollectionsResponse


@Composable
fun FilmsRoute(
    viewModel: FilmsViewModel = hiltViewModel(),
    screenType: String
) {

    val items = viewModel.getFilms(screenType).collectAsLazyPagingItems()
    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }

    FilmsScreen(
        items = items,
        width = width,
        height = height
    )

}

@Composable
internal fun FilmsScreen(
    items: LazyPagingItems<FilmsCollectionsDto.Item>,
    width: Dp,
    height: Dp,
) {

    Box {

        LazyVerticalGrid(
            modifier = Modifier
                .systemBarsPadding(),
            columns = GridCells.Fixed(2),
        ) {

            items(items.itemCount) { position ->
                items[position]?.let {
                    pictureItem(
                        item = it,
                        width = width / 2,
                        height = height / 3
                    )
                }
            }

        }

    }
}


@Composable
fun pictureItem(
    item: FilmsCollectionsDto.Item,
    width: Dp,
    height: Dp,
) {
    Column(
        modifier = Modifier.size(
            height = height,
            width = width
        )
    ) {
        AsyncImage(
            model = item.posterUrl,
            contentDescription = "Some descr",
            contentScale = ContentScale.Crop
        )
    }
}

enum class MainScreen() {
    TOP_250_MOVIES, TOP_250_TV_SHOWS
}