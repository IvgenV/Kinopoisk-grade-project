package com.example.kinopoisk.feature.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.films.FilmsRepository
import com.example.core.data.response.FilmsCollectionsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
): ViewModel() {

    private val _films = MutableStateFlow<List<FilmsCollectionsResponse.Item>>(emptyList())
    val films = _films.asStateFlow()

    fun getFilms(
        screenType: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            filmsRepository.getFilmsCollections(
                1,
                screenType
            )
        }.onFailure {
            val error = it.message
        }.onSuccess { response ->
            _films.update {
                 response.items?.filterNotNull() ?: emptyList()
            }
        }
    }

}