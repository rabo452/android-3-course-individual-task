package com.university_assignment.invididualtask.data.models.anime

data class AnimeModel(
    val title: String,
    val thumbnailUrl: String,
    val description: String,
    val seasonsCount: Int,
    val isFilmAvailable: Boolean = false,
)