package com.university_assignment.invididualtask.data.repository.interfaces

import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.anime.HomeAnimeModel
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import com.university_assignment.invididualtask.data.models.anime.SeasonAnimeModel

interface IAnimeRepository {

    /**
     * Get information about anime
     */
    suspend fun getAnimeMainInfo(): HomeAnimeModel?
    /**
     * Get general information about the anime from the web site:
     * title, description, rates, seasons count and so on
     */
    suspend fun getAnimeInfo(animeTitle: String): AnimeModel?

    /**
     * Get the information about the season, its episodes and title
     */
    suspend fun getSeasonInfo(animeTitle: String, seasonNumber: Int, isFilm: Boolean): SeasonAnimeModel?

    /**
     * Get the information about the episode, what exactly video providers support for what available language mode
     */
    suspend fun getEpisodeInfo(animeTitle: String, seasonNumber: Int, episodeNumber: Int, isFilm: Boolean): List<EpisodeStreamInfo>?
}