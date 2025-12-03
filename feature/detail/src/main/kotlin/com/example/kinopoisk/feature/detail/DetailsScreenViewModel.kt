package com.example.kinopoisk.feature.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.model.response.FilmDetailResponse
import com.example.core.data.model.response.ImagesResponseDto
import com.example.core.data.repository.films.FilmsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsScreenViewModel.Factory::class)
class DetailsScreenViewModel @AssistedInject constructor(
    @Assisted val kinopoiskId: Int,
    private val repository: FilmsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getDetailFilmInfo()
    }

    fun getDetailFilmInfo() = viewModelScope.launch {
        repository.getFilmDetail(kinopoiskId)
            .zip(
                repository.getFilmPoster(kinopoiskId, "STILL"),
                { filmDetails, posters ->
                    DetailsData(filmDetails, posters)
                }
            )
            .catch {
                Log.d("nbhnjkfnfg", "Error!")
            }
            .collect { filmData ->
                _uiState.value = createUiState(
                    filmDetailResponse = filmData.filmDetails,
                    posters = filmData.posters
                )
            }

    }


    private fun createUiState(
        filmDetailResponse: FilmDetailResponse,
        posters: List<FilmImages.FilmsItem>
    ): DetailsScreenState =
        with(filmDetailResponse) {
            return DetailsScreenState(
                coverUrl = coverUrl ?: posterUrl,
                title = nameRu,
                logoUrl = logoUrl,
                reviewsCount = reviewsCount ?: 0,
                ratingKinopoisk = ratingKinopoisk ?: 0.0,
                ratingKinopoiskVoteCount = ratingKinopoiskVoteCount ?: 0,
                nameOriginal = nameOriginal.orEmpty(),
                year = startYear ?: year,
                filmDuration = filmLength?.toFilmDuration(),
                genres = genres?.filterNotNull()?.take(2)?.map { it.genre.orEmpty() }.orEmpty(),
                posters = posters.mapNotNull { it.imageUrl }
            )
        }

    @AssistedFactory
    interface Factory {
        fun create(kinopoiskId: Int): DetailsScreenViewModel
    }

}

data class DetailsData(
    val filmDetails: FilmDetailResponse,
    val posters: List<FilmImages.FilmsItem>
)