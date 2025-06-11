package com.university_assignment.invididualtask.data.models.videoHoster

/**
 * data class for saving the link for m3u8 file (or analog) and http headers to get parts of the stream
 */
data class VideoStreamManifest(
    val sourceUrl: String,
    val httpHeaders: Map<String, String>
)