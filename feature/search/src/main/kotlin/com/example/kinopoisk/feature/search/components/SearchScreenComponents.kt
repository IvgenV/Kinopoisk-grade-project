package com.example.kinopoisk.feature.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsInputComposite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.model.dto.FilmsByFiltersDomain.ItemDomain
import com.example.kinopoisk.core.base.theme.KinopoiskTheme
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun SearchFilterMainScreen(
    request: String,
    onTextChange: (String) -> Unit,
    filterClicked: () -> Unit,
    films: LazyPagingItems<ItemDomain>
) {

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.primary),
    ) {

        FilterToolBar(
            onTextChange = onTextChange,
            filterClicked = filterClicked,
            request = request
        )

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            items(films.itemCount) { position ->

                films[position]?.let {
                    SearchableItem(it)
                }

            }

        }

    }

}

@Composable
internal fun FilterToolBar(
    request: String,
    onTextChange: (String) -> Unit,
    filterClicked: () -> Unit,
) {

    Row(
        Modifier.height(64.dp).background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )

        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            label = {
                Text("Фильмы, сериалы")
            },
            value = request,
            onValueChange = onTextChange,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Icon(
            imageVector = Icons.Default.SettingsInputComposite,
            contentDescription = null,
            modifier = Modifier.clickable {
                filterClicked.invoke()
            },
            tint = MaterialTheme.colorScheme.onPrimary
        )


    }

}

@Preview(showBackground = true)
@Composable
private fun FilterToolBarPreview() {
    KinopoiskTheme {
        FilterToolBar(
            onTextChange = { },
            filterClicked = { },
            request = "Матрица"
        )
    }
}

@Composable
internal fun SearchableItem(item: ItemDomain) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            modifier = Modifier
                .width(30.dp)
                .height(50.dp),
            placeholder = painterResource(com.example.kinopoisk.core.base.R.drawable.kinopoisk_poster_preview),
            model = item.posterUrlPreview,
            contentDescription = "Some descr",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.weight(1f),
            text = item.nameRu.orEmpty(),
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = "${item.ratingKinopoisk ?: 0}",
            color = MaterialTheme.colorScheme.onPrimary
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun SearchableItemPreview() {
    KinopoiskTheme {
        SearchableItem(
            ItemDomain(
                nameRu = "Матрица",
                ratingKinopoisk = 8.0
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchFilterScreenPreview() {

    KinopoiskTheme {

        SearchFilterMainScreen(
            request = "",
            onTextChange = {},
            filterClicked = {},
            films = flowOf(
                PagingData.from(
                    listOf(
                        ItemDomain(
                            nameRu = "Матртица",
                            ratingKinopoisk = 8.0
                        ),
                        ItemDomain(
                            nameRu = "Матртица",
                            ratingKinopoisk = 8.0
                        ),
                        ItemDomain(
                            nameRu = "Матртица",
                            ratingKinopoisk = 8.0
                        ),
                    )
                )
            ).collectAsLazyPagingItems(),
        )
    }
}
