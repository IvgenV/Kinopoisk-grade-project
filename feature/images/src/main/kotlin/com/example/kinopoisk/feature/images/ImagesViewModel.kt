package com.example.kinopoisk.feature.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.model.dto.FilmImages
import com.example.core.data.repository.films.FilmsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel(assistedFactory = ImagesViewModel.Factory::class)
class ImagesViewModel @Inject constructor(
    @Assisted val filmId: Int,
    private val repository: FilmsRepository
) : ViewModel() {

    fun getImages(type: String): Flow<PagingData<FilmImages.FilmsItem>> =
        repository.getFilmImagesPaging(filmId = filmId, type = type).cachedIn(viewModelScope)


    @AssistedFactory
    interface Factory {
        fun create(kinopoiskId: Int): ImagesViewModel
    }

}

