package com.university_assignment.invididualtask.data.models

data class EpisodeStreamManifest(
    val sourceUrl: String,
    val httpHeaders: Map<String, String>
)