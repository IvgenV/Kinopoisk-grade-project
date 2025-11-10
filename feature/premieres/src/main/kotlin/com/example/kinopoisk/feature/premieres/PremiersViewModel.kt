package com.example.kinopoisk.feature.premieres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.dto.PremierItemDto
import com.example.core.data.repository.films.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PremiersViewModel @Inject constructor(
    private val repository: FilmsRepository
): ViewModel() {

    init {
        getPremiers(
            2025, "JULY"
        )
    }

    private val _premiers = MutableStateFlow<List<PremierItemDto>>(emptyList())
    val premiers = _premiers.asStateFlow()

    fun getPremiers(year: Int, month: String) = viewModelScope.launch {
        runCatching {
            repository.getFilmsPremieres(year = year, month = month)
        }.onSuccess { response ->
            _premiers.value = response
        }.onFailure {

        }
    }

}