package com.example.core.data.model.dto

import androidx.compose.runtime.Immutable

@Immutable
data class FilmImages(
    val item: List<FilmsItem?>? = null,
) {
    @Immutable
    data class FilmsItem(
        val imageUrl: String? = null,
        val previewUrl: String? = null,
        val id: Int? = null,
    )
}