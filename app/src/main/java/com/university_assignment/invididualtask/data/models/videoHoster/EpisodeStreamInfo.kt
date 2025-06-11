package com.university_assignment.invididualtask.data.models.videoHoster

import com.university_assignment.invididualtask.data.models.anime.EpisodeLanguage

data class EpisodeStreamInfo(
    val url: String,
    val videoHoster: VideoHoster,
    val language: EpisodeLanguage
)