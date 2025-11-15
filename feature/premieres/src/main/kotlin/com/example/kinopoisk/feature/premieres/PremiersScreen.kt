package com.example.kinopoisk.feature.premieres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core.data.model.dto.PremierItemDto


@Composable
fun PremiersRoute(
    viewModel: PremiersViewModel = hiltViewModel()
) {

    val premiers = viewModel.premiers.collectAsState()

    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }

    PremiersScreen(
        premiers.value,
        width = width,
        height = height
    )

}

@Composable
internal fun PremiersScreen(
    premiers: List<PremierItemDto>,
    width: Dp,
    height: Dp
) {

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) { }

    Box(Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(premiers) { item ->
                AsyncImage(
                    modifier = Modifier
                        .width(width / 2)
                        .height(height / 3),
                    model = item.posterUrl,
                    contentDescription = "Some descr",
                    contentScale = ContentScale.Crop
                )
            }

        }

    }

}