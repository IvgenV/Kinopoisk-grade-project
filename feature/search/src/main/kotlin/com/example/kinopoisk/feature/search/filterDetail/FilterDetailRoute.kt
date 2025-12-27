package com.example.kinopoisk.feature.search.filterDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.data.model.dto.FilmFilter.Genre
import com.example.core.data.model.dto.SearchDetailFilter
import com.example.core.data.model.dto.SearchDetailFilter.CountryFilter
import com.example.core.data.model.dto.SearchDetailFilter.GenreFilter
import com.example.core.data.model.dto.SearchDetailFilter.YearsFilter
import com.example.core.data.model.dto.getTitle
import com.example.kinopoisk.core.base.theme.KinopoiskTheme
import com.example.kinopoisk.feature.search.components.FilterDetailTopAppBar

@Composable
internal fun FilterDetailScreen(
    searchDetailFilter: SearchDetailFilter,
    selectedItem: String?,
    onClick: (String) -> Unit,
    onBack: () -> Unit,
) {

    var text by rememberSaveable { mutableStateOf("") }

    BackHandler(enabled = true) {
        onBack.invoke()
    }

    Column(
        Modifier.background(MaterialTheme.colorScheme.primary)
    ) {

        FilterDetailTopAppBar(
            titleText = searchDetailFilter.getTitle(),
            icon = Icons.Default.ArrowBackIosNew,
            onIconClick = {
                onBack.invoke()
            },
            resetClick = {

            }
        )

        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .background(MaterialTheme.colorScheme.primary)
        ) {

            item {
                OutlinedTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    placeholder = { Text("Введите название") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear text",
                                modifier = Modifier
                                    .clickable {
                                        text = ""
                                    }
                            )
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            items(
                when (searchDetailFilter) {
                    is CountryFilter -> {
                        searchDetailFilter.countries.map { it.country }
                    }

                    is GenreFilter -> {
                        searchDetailFilter.genres.map { it.genre }
                    }

                    YearsFilter -> {
                        listOf(
                            "2025",
                            "2024",
                            "2023",
                        )
                    }
                }
            ) {

                Column(
                    Modifier.clickable {
                        onClick.invoke(it)
                    }
                ) {
                    Row {

                        Text(
                            modifier = Modifier.weight(1f),
                            text = it,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        if (selectedItem == it) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                    }
                    HorizontalDivider()
                }

            }

        }

    }


}

@Preview(showBackground = true)
@Composable
private fun FilterDetailScreenPreview() {
    KinopoiskTheme {
        FilterDetailScreen(
            searchDetailFilter = GenreFilter(
                genres = arrayListOf(
                    Genre(
                        genre = "Боевики"
                    ),
                    Genre(
                        genre = "Ужастики"
                    ),
                    Genre(
                        genre = "Драммы"
                    ),

                    )
            ),
            selectedItem = "Боевики",
            onClick = {},
            onBack = {}
        )
    }
}