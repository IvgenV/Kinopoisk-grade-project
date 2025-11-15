package com.example.kinopoisk.feature.films

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FilmsFilterScreen(
    selectedFilter: FilmsCollection = FilmsCollection.TOP_POPULAR_ALL,
    onClick: (FilmsCollection) -> Unit,
) {

    val selectedFilter = remember {
        mutableStateOf(selectedFilter)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onClick.invoke(selectedFilter.value)
            }
        ) {

            Text("Apply filters")

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .clip(RoundedCornerShape(48.dp))
        ) {


            items(
                FilmsCollection.entries
            ) { item ->

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = item.name == selectedFilter.value.name,
                        onClick = {
                            selectedFilter.value = item
                        }
                    )
                    Text(item.name)
                }

            }

        }

    }

}

enum class FilmsCollection {
    TOP_POPULAR_ALL, TOP_POPULAR_MOVIES, TOP_250_TV_SHOWS, TOP_250_MOVIES, COMICS_THEME, KIDS_ANIMATION_THEME
}

@Preview(showBackground = true)
@Composable
private fun FilmsFilterScreenPreview() {
    FilmsFilterScreen {

    }
}