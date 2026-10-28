package com.example.kinopoisk.feature.detail

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.kinopoisk.core.base.theme.KinopoiskTheme
import com.example.kinopoisk.core.base.R as BaseRes

@Composable
fun DetailsScreenRoute(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    toAllFilmImages: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    DetailScreen(
        uiState = uiState,
        kinopoiskFilmId = viewModel.kinopoiskId,
        toAllFilmImages = toAllFilmImages
    )


}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreen(
    uiState: DetailsScreenState,
    kinopoiskFilmId: Int,
    toAllFilmImages: (Int) -> Unit,
) {

    val conf = LocalConfiguration.current
    val height = remember { conf.screenHeightDp.dp / 2 }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        item {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height),
                model = uiState.coverUrl,
                placeholder = painterResource(BaseRes.drawable.kinopoisk_poster_preview),
                contentDescription = "Some descr",
                contentScale = ContentScale.Crop
            )
        }

        item {

            uiState.logoUrl?.let {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(16.dp),
                    model = uiState.logoUrl,
                    placeholder = painterResource(BaseRes.drawable.logo_preview),
                    contentDescription = "Some descr",
                    contentScale = ContentScale.Crop
                )
            } ?: Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                text = uiState.title.orEmpty()
            )

        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = "${uiState.ratingKinopoisk}"
                )

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = "${uiState.ratingKinopoiskVoteCount}"
                )

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = uiState.nameOriginal
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = 4.dp),
                    text = uiState.year?.toString().orEmpty()
                )
                uiState.genres.forEach {
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(end = 4.dp),
                        text = it
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.country.forEachIndexed { index, country ->
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(end = 4.dp),
                        text = if (uiState.country.lastIndex == index) {
                            country
                        } else {
                            "${country}, "
                        }
                    )
                }
                Text(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(end = 4.dp),
                    text = uiState.filmDuration.orEmpty()
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 32.dp),
                color = Gray
            )

            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = uiState.description.orEmpty()
            )
        }

        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Изображения",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        modifier = Modifier.clickable {
                            toAllFilmImages.invoke(kinopoiskFilmId)
                        },
                        text = "Все",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                LazyRow(
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.posters) {
                        AsyncImage(
                            modifier = Modifier
                                .width(250.dp)
                                .height(120.dp),
                            model = it,
                            placeholder = painterResource(BaseRes.drawable.kinopoisk_poster_preview),
                            contentDescription = "Some descr",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

    }

}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailScreenPreview() {
    KinopoiskTheme {
        DetailScreen(
            uiState = DetailsScreenState(
                title = "Матрица",
                nameOriginal = "Matrix",
                logoUrl = "sdsf",
                ratingKinopoisk = 8.0,
                ratingKinopoiskVoteCount = 107000,
                country = listOf("USA", "Britain", "Italy"),
                filmDuration = "2ч 55м",
                description = "1980-е годы, тихий провинциальный американский городок. Благоприятное течение местной жизни нарушает загадочное исчезновение подростка по имени Уилл. Выяснить обстоятельства дела полны решимости родные мальчика и местный шериф, также события затрагивают лучшего друга Уилла – Майка. Он начинает собственное расследование. Майк уверен, что близок к разгадке, и теперь ему предстоит оказаться в эпицентре ожесточенной битвы потусторонних сил.",
                year = 2022,
                genres = listOf("криминал", "детектив")
            ),
            kinopoiskFilmId = 0,
            toAllFilmImages = { },
        )
    }
}