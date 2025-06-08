package com.university_assignment.invididualtask.data.models

data class EpisodeModel(
    val title: String,
    val number: Number,
    val languages: Array<EpisodeLanguage>,
    val videoHosters: Array<VideoHoster>
)
