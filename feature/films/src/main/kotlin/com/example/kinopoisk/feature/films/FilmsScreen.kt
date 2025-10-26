package com.example.kinopoisk.feature.films

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun filmsScreen(
    modifier: Modifier,
    viewModel: FilmsViewModel = hiltViewModel()
) {

    Box(
        modifier = modifier
    ) {

        Button(
            onClick = {
                viewModel.getFilms()
            }
        ) {
            Text("Click!")
        }


    }
}