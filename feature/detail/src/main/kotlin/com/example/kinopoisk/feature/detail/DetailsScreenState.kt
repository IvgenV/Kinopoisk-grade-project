package com.example.kinopoisk.feature.detail

import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class DetailsScreenState(
    val coverUrl: String? = null,
    val logoUrl: String? = null,
    val title: String? = null,
    val reviewsCount: Int = 0,
    val ratingKinopoisk: Double = 0.0,
    val ratingKinopoiskVoteCount: Int = 0,
    val nameOriginal: String = "",
    val country: List<String> = emptyList(),
    val filmDuration: String? = null,
    val description: String? = null,
    val year: Int? = null,
    val genres: List<String> = listOf(),
    val posters: List<String> = listOf()
)


fun Int.toFilmDuration(): String {

    val duration = this.toDuration(DurationUnit.MINUTES)

    val hours = duration.inWholeHours.toInt()
    val minutes = duration.minus(hours.toDuration(DurationUnit.HOURS)).inWholeMinutes.toInt()

    return buildString {
        if (hours > 0) {
            append("$hours ч ")
        }
        if (minutes > 0) {
            append("$minutes м")
        }
    }.trim()

}