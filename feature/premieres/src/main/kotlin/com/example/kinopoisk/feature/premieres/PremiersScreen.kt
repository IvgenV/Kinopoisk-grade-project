package com.example.kinopoisk.feature.premieres

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LongState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
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

    Box(Modifier.fillMaxSize()) {

        LazyColumn {

            items(premiers) { item ->
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

        }

    }

}