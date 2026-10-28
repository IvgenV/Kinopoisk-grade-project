package com.example.kinopoisk.feature.films

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.kinopoisk.core.base.theme.KinopoiskTheme
import kotlinx.coroutines.flow.flowOf


@Composable
internal fun FilmsItem(
    itemDomain: FilmsByFiltersDomain.ItemDomain,
    width: Dp,
    height: Dp,
    onClick: (FilmsByFiltersDomain.ItemDomain) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .size(
                height = height,
                width = width
            )
            .background(MaterialTheme.colorScheme.primary)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick.invoke(itemDomain)
                }
                .height(height - 30.dp),
            placeholder = painterResource(com.example.kinopoisk.core.base.R.drawable.kinopoisk_poster_preview),
            model = itemDomain.posterUrl,
            contentDescription = "Some descr",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = itemDomain.nameRu.orEmpty(),
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun FilmsItemPreview() {
    KinopoiskTheme {
        FilmsItem(
            itemDomain = FilmsByFiltersDomain.ItemDomain(
                nameRu = "Матрица"
            ),
            width = 150.dp,
            height = 300.dp,
        )
    }
}

@Composable
internal fun FilmsScreen(
    items: LazyPagingItems<FilmsByFiltersDomain.ItemDomain>,
    onItemClicked: (FilmsByFiltersDomain.ItemDomain) -> Unit,
) {

    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }

    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.primary),
        state = scrollState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(
            count = items.itemCount,
        ) { position ->

            items[position]?.let {
                FilmsItem(
                    itemDomain = it,
                    width = width / 2,
                    height = height / 3,
                    onClick = onItemClicked,
                )
            }

        }

    }

}


@Preview(showBackground = true)
@Composable
private fun FilmsScreenPreview() {
    KinopoiskTheme {
        FilmsScreen(
            items = flowOf(
                PagingData.from(
                    listOf(
                        FilmsByFiltersDomain.ItemDomain(
                            nameRu = "Матрица"
                        ),
                        FilmsByFiltersDomain.ItemDomain(
                            nameRu = "Матрица"
                        ),
                        FilmsByFiltersDomain.ItemDomain(
                            nameRu = "Матрица"
                        ),
                        FilmsByFiltersDomain.ItemDomain(
                            nameRu = "Матрица"
                        ),
                        FilmsByFiltersDomain.ItemDomain(
                            nameRu = "Матрица"
                        )
                    )
                )
            ).collectAsLazyPagingItems(),
            onItemClicked = {}
        )
    }
}