package com.university_assignment.invididualtask.data.models.anime

data class SeasonAnimeModel(
    val seasonNumber: Int,
    val isFilm: Boolean,
    val episodes: Array<EpisodeModel>
)