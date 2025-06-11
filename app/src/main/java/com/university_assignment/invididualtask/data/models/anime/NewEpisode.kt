package com.university_assignment.invididualtask.data.models.anime

data class NewEpisode(
    val title: String,
    val arrivalTime: String,
    val language: EpisodeLanguage,
    val episodeNumber: Int,
    val seasonNumber: Int
)
