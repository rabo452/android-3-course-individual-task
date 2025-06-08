package com.university_assignment.invididualtask.api.interfaces

import com.university_assignment.invididualtask.data.models.AnimeModel
import com.university_assignment.invididualtask.data.models.SeasonAnimeModel

interface IParser {
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
}