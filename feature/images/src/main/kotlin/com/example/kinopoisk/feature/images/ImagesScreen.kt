package com.example.kinopoisk.feature.images

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.model.dto.FilmImages
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ImagesRoute(
    viewModel: ImagesViewModel
) {

    val items = viewModel.getImages("STILL").collectAsLazyPagingItems()

    val scrollState = rememberLazyGridState()

    var showDetails by remember {
        mutableStateOf(false)
    }
    var selectedItem: FilmImages.FilmsItem? by remember {
        mutableStateOf(null)
    }

    SharedTransitionLayout {

        AnimatedContent(
            showDetails,
        ) { targetState ->

            if (!targetState) {
                ImagesScreen(
                    items = items,
                    scrollState = scrollState,
                    onShowDetails = { item ->
                        selectedItem = item
                        showDetails = true
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                BackHandler {
                    showDetails = false
                }
                selectedItem?.let {
                    ImageItemDetail(
                        imageItem = it,
                        onItemClicked = {
                            selectedItem = null
                            showDetails = false
                        },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }

        }

    }

}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ImagesScreen(
    items: LazyPagingItems<FilmImages.FilmsItem>,
    scrollState: LazyGridState,
    onShowDetails: (FilmImages.FilmsItem) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    Box(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                count = items.itemCount,
            ) { position ->
                items[position]?.let { imageItem ->
                    ImageItem(
                        imageItem.copy(
                            id = position
                        ),
                        onShowDetails = onShowDetails,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                }
            }

        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ImageItem(
    imageItem: FilmImages.FilmsItem,
    onShowDetails: (FilmImages.FilmsItem) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }


    with(sharedTransitionScope) {


        AsyncImage(
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(
                        key = imageItem.id ?: 0
                    ),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clickable {
                    onShowDetails.invoke(imageItem)
                }
                .width(width / 2)
                .height(height / 3),
            placeholder = painterResource(com.example.kinopoisk.core.base.R.drawable.kinopoisk_poster_preview),
            model = imageItem.imageUrl,
            contentDescription = "Some descr",
            contentScale = ContentScale.Crop
        )

    }


}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ImageItemDetail(
    imageItem: FilmImages.FilmsItem,
    onItemClicked: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    with(sharedTransitionScope) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            AsyncImage(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = imageItem.id ?: 0),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .clickable {
                        onItemClicked.invoke()
                    }
                    .fillMaxSize(),
                placeholder = painterResource(com.example.kinopoisk.core.base.R.drawable.kinopoisk_poster_preview),
                model = imageItem.imageUrl,
                contentDescription = "Some descr",
                contentScale = ContentScale.Crop
            )

        }


    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showSystemUi = true)
@Composable
private fun filmImagesPreview() {

    SharedTransitionLayout {

        AnimatedContent(
            true,
            label = "basic_transition"
        ) { _ ->
            ImagesScreen(
                items = flowOf(
                    PagingData.from(
                        listOf(
                            FilmImages.FilmsItem(
                                imageUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
                            ),
                            FilmImages.FilmsItem(
                                imageUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
                            ),
                            FilmImages.FilmsItem(
                                imageUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
                            ),
                        )
                    )
                ).collectAsLazyPagingItems(),
                scrollState = rememberLazyGridState(),
                onShowDetails = {},
                animatedVisibilityScope = this@AnimatedContent,
                sharedTransitionScope = this@SharedTransitionLayout
            )
        }


    }
}