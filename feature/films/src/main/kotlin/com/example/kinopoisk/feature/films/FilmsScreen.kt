package com.example.kinopoisk.feature.films

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.core.data.model.dto.FilmsCollectionsDto


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilmsRoute(
    viewModel: FilmsViewModel = hiltViewModel(),
    screenType: String,
    resultState: String = "",
    toBottomShet: () -> Unit = {}
) {

    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }

    val items: LazyPagingItems<FilmsCollectionsDto.Item> =
        viewModel.getFilms(screenType).collectAsLazyPagingItems()

    var showDetails by remember {
        mutableStateOf(false)
    }

    var selectedItem: FilmsCollectionsDto.Item? by remember {
        mutableStateOf(null)
    }

    val scrollState = rememberLazyGridState()


        SharedTransitionLayout {

            AnimatedContent(
                showDetails,
                label = "khjbhkdjfkgfnkgf"
            ) { targetState ->

                if (!targetState) {



                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        Button(
                            onClick = {
                                //toBottomShet.invoke()
                            }
                        ) {
                            Text("to bottom sheet")
                        }

                        Text("resultState")

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

                                items[position]?.let {
                                    pictureItem(
                                        item = it,
                                        width = width / 2,
                                        height = height / 3,
                                        onClick = {
                                            selectedItem = it
                                            showDetails = true
                                        },
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        animatedVisibilityScope = this@AnimatedContent,
                                    )
                                }


                            }

                        }
                    }


//                MainContent(
//                    items = items,
//                    width = width,
//                    height = height,
//                    onClick = {
//                        selectedItem = it
//                        showDetails = true
//                    },
//                    animatedVisibilityScope = this@AnimatedContent,
//                    sharedTransitionScope = this@SharedTransitionLayout
//                )
                } else {
                    BackHandler { showDetails = false }
                    DetailContent(
                        width = width,
                        height = height,
                        item = selectedItem!!,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }

            }

        }

    }


//    FilmsScreen(
//        items = items.itemSnapshotList,
//        width = width,
//        height = height,
//        resultState = resultState,
//        toBottomShet = toBottomShet
//    )


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun FilmsScreen(
    items: ItemSnapshotList<FilmsCollectionsDto.Item>,
    width: Dp,
    height: Dp,
    resultState: String,
    toBottomShet: () -> Unit = {}
) {

    var showDetails by remember {
        mutableStateOf(false)
    }

    var selectedItem: FilmsCollectionsDto.Item? by remember {
        mutableStateOf(null)
    }

    SharedTransitionLayout {

        AnimatedContent(
            showDetails,
            label = "khjbhkdjfkgfnkgf"
        ) { targetState ->

            if (!targetState) {
                MainContent(
                    items = items,
                    width = width,
                    height = height,
                    onClick = {
                        selectedItem = it
                        showDetails = true
                    },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                BackHandler { showDetails = false }
                DetailContent(
                    width = width,
                    height = height,
                    item = selectedItem!!,
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }

        }

    }


}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailContent(
    item: FilmsCollectionsDto.Item,
    width: Dp,
    height: Dp,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    with(sharedTransitionScope) {

        Column(
            Modifier.fillMaxSize()
        ) {

            AsyncImage(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = item.kinopoiskId ?: 0),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxWidth()
                    .height(height - 30.dp),
                placeholder = painterResource(R.drawable.x1000),
                model = item.posterUrl,
                contentDescription = "Some descr",
                contentScale = ContentScale.Crop
            )
        }

    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MainContent(
    items: ItemSnapshotList<FilmsCollectionsDto.Item>,
    width: Dp,
    height: Dp,
    onClick: (FilmsCollectionsDto.Item) -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Button(
            onClick = {
                //toBottomShet.invoke()
            }
        ) {
            Text("to bottom sheet")
        }

        Text("resultState")

        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(
                items = items.items,
            ) {
                pictureItem(
                    item = it,
                    width = width / 2,
                    height = height / 3,
                    onClick = onClick,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }

        }
    }

}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun pictureItem(
    item: FilmsCollectionsDto.Item,
    width: Dp,
    height: Dp,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: (FilmsCollectionsDto.Item) -> Unit = {},
) {

    with(sharedTransitionScope) {

        Column(
            modifier = Modifier.size(
                height = height,
                width = width
            )
        ) {
            AsyncImage(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = item.kinopoiskId ?: 0),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxWidth()
                    .clickable {
                        onClick.invoke(item)
                    }
                    .height(height - 30.dp),
                placeholder = painterResource(R.drawable.x1000),
                model = item.posterUrl,
                contentDescription = "Some descr",
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = item.nameRu.orEmpty()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun screenPreview() {
    val conf = LocalConfiguration.current

    val width = remember { conf.screenWidthDp.dp }
    val height = remember { conf.screenHeightDp.dp }


//    FilmsScreen(
//        items = flowOf(
//            PagingData.from(
//                listOf(
//                    FilmsCollectionsDto.Item(
//                        nameRu = "Мстители",
//                        posterUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
//                    ),
//                    FilmsCollectionsDto.Item(
//                        nameRu = "Мстители",
//                        posterUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
//                    ),
//                    FilmsCollectionsDto.Item(
//                        nameRu = "Мстители",
//                        posterUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
//                    )
//                )
//            )
//        ).collectAsLazyPagingItems(),
//        width = width,
//        height = height,
//        resultState = "Films"
//    )
}

//@Preview(showBackground = true)
//@Composable
//private fun pictureIyemPreview() {
//    pictureItem(
//        item = FilmsCollectionsDto.Item(
//            posterUrl = "http://kinopoiskapiunofficial.tech/images/posters/kp/263531.jpg"
//        ),
//        100.dp,
//        200.dp
//    )
//}