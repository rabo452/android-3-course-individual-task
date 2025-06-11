package com.university_assignment.invididualtask.data.models.anime

import com.university_assignment.invididualtask.data.models.videoHoster.VideoHoster

data class EpisodeModel(
    val title: String,
    val number: Int,
    val languages: Array<EpisodeLanguage>,
    val videoHosters: Array<VideoHoster>
)
