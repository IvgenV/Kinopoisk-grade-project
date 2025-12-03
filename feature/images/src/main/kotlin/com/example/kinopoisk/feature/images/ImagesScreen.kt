package com.example.kinopoisk.feature.images

import android.media.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.data.model.dto.FilmImages

@Composable
internal fun ImagesRoute(
    viewModel: ImagesViewModel
) {

    val items = viewModel.getImages("STILL").collectAsLazyPagingItems()

    val st =  items.itemSnapshotList

}

@Composable
private fun ImagesScreen(
    items: LazyPagingItems<FilmImages.FilmsItem>
) {

    LazyColumn {



    }

}