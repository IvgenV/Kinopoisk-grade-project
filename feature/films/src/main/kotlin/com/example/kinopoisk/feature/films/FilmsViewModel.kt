package com.example.kinopoisk.feature.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.model.dto.FilmsByFiltersDomain
import com.example.core.data.repository.films.FilmsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

@HiltViewModel(assistedFactory = FilmsViewModel.Factory::class)
class FilmsViewModel @AssistedInject constructor(
    @Assisted screenType: String,
    filmsRepository: FilmsRepository
) : ViewModel() {

    val films: Flow<PagingData<FilmsByFiltersDomain.ItemDomain>> = filmsRepository.getFilmsCollections(
        screenType
    ).cachedIn(viewModelScope)

    @AssistedFactory
    interface Factory {
        fun create(screenType: String): FilmsViewModel
    }

}