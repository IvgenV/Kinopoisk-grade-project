package com.example.core.data.model.response


import com.example.core.data.model.dto.FilmImages
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResponseDto(
    @Json(name = "items")
    val items: List<ItemDto?>? = null,
    @Json(name = "total")
    val total: Int? = null,
    @Json(name = "totalPages")
    val totalPages: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class ItemDto(
        @Json(name = "imageUrl")
        val imageUrl: String? = null,
        @Json(name = "previewUrl")
        val previewUrl: String? = null
    )
}

fun ImagesResponseDto.ItemDto.toDomain() = FilmImages.FilmsItem(
    imageUrl = imageUrl,
    previewUrl = previewUrl
)