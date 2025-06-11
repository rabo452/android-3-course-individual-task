package com.university_assignment.invididualtask.data.models.anime

/**
 * Model for storing the information for main page, like recommendations, news and so on
 */
data class HomeAnimeModel(
    val animeRecommendation: Array<BriefAnimeModel>,
    val newEpisodes: Array<NewEpisode>,
    val newAnimes: Array<BriefAnimeModel>,
    val likedAnimes: Array<BriefAnimeModel>
)
