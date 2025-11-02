package com.example.kinopoisk.feature.films

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.response.FilmsCollectionsResponse

@Composable
fun filmsScreen(
    modifier: Modifier = Modifier,
    screenType: String,
    viewModel: FilmsViewModel = hiltViewModel()
) {

    val conf = LocalConfiguration.current

    val width = conf.screenWidthDp.dp
    val height = conf.screenHeightDp.dp

    val items = viewModel.getfm(screenType).collectAsLazyPagingItems()

    Box(
        modifier = modifier
    ) {

        LazyVerticalGrid(
            modifier = Modifier
                .systemBarsPadding(),
            columns = GridCells.Fixed(2),
        ) {

            items(items.itemCount) { position ->
                items[position]?.let {
                    pictureItem(
                        item = it,
                        width = width/2,
                        height = height/3
                    )
                }
            }

        }

    }
}


@Composable
fun pictureItem(
    item: FilmsCollectionsResponse.Item,
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