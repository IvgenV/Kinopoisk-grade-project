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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.data.model.dto.SearchDetailFilter
import com.example.core.data.model.dto.SearchDetailFilter.CountryFilter
import com.example.core.data.model.dto.SearchDetailFilter.GenreFilter
import com.example.core.data.model.dto.SearchDetailFilter.YearsFilter
import com.example.kinopoisk.core.base.theme.KinopoiskTheme
import com.example.kinopoisk.feature.search.data.ProductType
import com.example.kinopoisk.feature.search.data.SearchFilterSort
import com.example.kinopoisk.feature.search.data.SearchFilterSort.NUM_VOTE
import com.example.kinopoisk.feature.search.data.SearchFilterSort.RATING
import com.example.kinopoisk.feature.search.data.SearchFilterUiState


@Composable
internal fun SearchFilterScreen(
    filterUiState: SearchFilterUiState,
    applyFilter: () -> Unit,
    filterDetailClick: (SearchDetailFilter) -> Unit,
    sortClick: (SearchFilterSort) -> Unit,
    typeClick: (ProductType) -> Unit,
    highRatingCheck: (Boolean) -> Unit,
    restUiState: () -> Unit,
) {


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {

        FilterDetailTopAppBar(
            titleText = "Поиск лучших фильмов",
            onIconClick = applyFilter,
            resetClick = {
                restUiState.invoke()
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            item {
                FilterItem(
                    item = Pair("Страны", filterUiState.selectedCountry ?: "Все страны"),
                    onClick = {
                        filterDetailClick.invoke(
                            CountryFilter(
                                countries = filterUiState.countries ?: arrayListOf()
                            )
                        )
                    }
                )
            }

            item {
                FilterItem(
                    item = Pair("Жанры", filterUiState.selectedGenre ?: "Все жанры"),
                    onClick = {
                        filterDetailClick.invoke(
                            GenreFilter(
                                genres = filterUiState.genres ?: arrayListOf()
                            )
                        )
                    }
                )
            }

            item {
                FilterItem(
                    item = Pair("Годы", filterUiState.selectedYear ?: "Годы"),
                    onClick = {
                        filterDetailClick.invoke(YearsFilter)
                    }
                )
            }

            item {
                SortItem(
                    searchFilterSort = filterUiState.sortedBy ?: RATING,
                    onItemSelected = {
                        sortClick.invoke(it)
                    }
                )
            }

            item {
                TypeItem(
                    productType = filterUiState.productType ?: ProductType.ALL,
                    typeClick = {
                        typeClick.invoke(it)
                    }
                )
            }

            item {
                IsHighRating(
                    isHighRatingCheck = filterUiState.isHighRating,
                    onCheckedChanges = {
                        highRatingCheck.invoke(it)
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchFilterScreenPreview() {
    KinopoiskTheme {
        SearchFilterScreen(
            SearchFilterUiState(
                selectedCountry = "USA",
                selectedGenre = "Боевики",
                selectedYear = "2024",
                sortedBy = NUM_VOTE
            ),
            applyFilter = {},
            filterDetailClick = {},
            sortClick = {},
            typeClick = {},
            highRatingCheck = {},
            restUiState = {}
        )
    }
}


@Composable
internal fun FilterDetailTopAppBar(
    titleText: String,
    onIconClick: () -> Unit,
    resetClick: () -> Unit,
    icon: ImageVector = Icons.Default.Check
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopAppBarDefaults.TopAppBarExpandedHeight)
            .padding(horizontal = 6.dp)
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.clickable {
                onIconClick.invoke()
            }
        )

        Text(
            modifier = Modifier.weight(1f),
            text = titleText,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.clickable {
                resetClick.invoke()
            },
            color = MaterialTheme.colorScheme.onPrimary,
            text = "Сбросить",
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun FilterDetailTopAppBarPreview() {
    KinopoiskTheme {
        FilterDetailTopAppBar(
            titleText = "Поиск лучших фильмов",
            onIconClick = { },
            resetClick = { }
        )
    }
}

@Composable
internal fun FilterItem(
    item: Pair<String, String>,
    onClick: (String) -> Unit
) {

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .clickable {
                    onClick.invoke(item.first)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = item.first,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )

            Text(
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onPrimary,
                text = item.second,
                modifier = Modifier.weight(1f)
            )

        }

        HorizontalDivider(
            Modifier
                .padding(top = 6.dp)
        )

    }


}

@Preview(showBackground = true)
@Composable
private fun FilterItemPreview() {
    KinopoiskTheme {
        FilterItem(
            item = Pair("Страны", "Россия"),
            onClick = {}
        )
    }
}

@Composable
internal fun SortItem(
    searchFilterSort: SearchFilterSort,
    onItemSelected: (SearchFilterSort) -> Unit,
) {

    var selectableIndex by remember {
        mutableIntStateOf(SearchFilterSort.entries.indexOf(searchFilterSort))
    }

    Column(
        Modifier.background(MaterialTheme.colorScheme.primary)
    ) {

        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = "Сортировать по",
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        PrimaryTabRow(
            contentColor = MaterialTheme.colorScheme.primary,
            selectedTabIndex = selectableIndex,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectableIndex,
                        matchContentSize = true
                    ),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        ) {
            SearchFilterSort.entries.forEachIndexed { index, filter ->
                Tab(
                    selected = selectableIndex == index,
                    onClick = {
                        onItemSelected.invoke(filter)
                        selectableIndex = index
                    },
                    text = {
                        Text(
                            text = filter.sortName,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }

    }

}


@Composable
internal fun TypeItem(
    productType: ProductType,
    typeClick: (ProductType) -> Unit,
) {
    var selectableIndex by remember {
        mutableIntStateOf(ProductType.entries.indexOf(productType))
    }

    Column(
        Modifier.background(MaterialTheme.colorScheme.primary)
    ) {

        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = "Тип",
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        PrimaryTabRow(
            contentColor = MaterialTheme.colorScheme.primary,
            selectedTabIndex = selectableIndex,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectableIndex,
                        matchContentSize = true
                    ),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        ) {
            ProductType.entries.forEachIndexed { index, filter ->
                Tab(
                    selected = selectableIndex == index,
                    onClick = {
                        typeClick.invoke(filter)
                        selectableIndex = index
                    },
                    text = {
                        Text(
                            text = filter.sortName,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun SortItemPreview() {
    KinopoiskTheme {
        SortItem(
            searchFilterSort = NUM_VOTE,
            onItemSelected = { }
        )
    }
}

@Composable
internal fun IsHighRating(
    isHighRatingCheck: Boolean,
    onCheckedChanges: (Boolean) -> Unit,
) {

    var checked by remember { mutableStateOf(isHighRatingCheck) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            // Toggles the entire row on click for better accessibility
            .toggleable(
                value = checked,
                onValueChange = {
                    checked = it
                    onCheckedChanges.invoke(it)
                },
                role = Role.Switch
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Высокий рейтинг",
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Switch(
            checked = checked,
            onCheckedChange = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IsHighRatingPreview() {
    KinopoiskTheme {
        IsHighRating(
            isHighRatingCheck = true,
            onCheckedChanges = { }
        )
    }
}
