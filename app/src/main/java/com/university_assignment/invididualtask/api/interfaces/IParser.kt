package com.university_assignment.invididualtask.api.interfaces

import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.anime.HomeAnimeModel
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import com.university_assignment.invididualtask.data.models.anime.SeasonAnimeModel

interface IParser {

    /**
     * Get the information from the main page of the website
     */
    fun getMainPageInfo(pageBody: String): HomeAnimeModel?
    /**
     * Get general anime information from the main page of the anime
     * @param pageBody the HTML document from the website
     */
    fun getAnimeInfo(pageBody: String): AnimeModel?

    /**
     * Get the information about the current active season, its episodes and title
     * @param pageBody the HTML document from the website
     */
    fun getSeasonInfo(pageBody: String): SeasonAnimeModel?

    /**
     * Get the information about the host links and what providers are available
     */
    fun getEpisodeStreamInfo(pageBody: String): List<EpisodeStreamInfo>?
}