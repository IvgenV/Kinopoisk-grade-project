package com.example.kinopoisk.feature.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.films.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
): ViewModel() {


    fun getFilms() = viewModelScope.launch {
        delay(1500)
        runCatching {
            filmsRepository.getFilmsCollections(
                1,
                "TOP_250_MOVIES"
            )
        }.onFailure {
            val error = it.message
        }
    }

}