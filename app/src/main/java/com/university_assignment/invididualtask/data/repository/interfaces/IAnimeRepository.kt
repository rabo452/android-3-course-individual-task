package com.university_assignment.invididualtask.data.repository.interfaces

import com.university_assignment.invididualtask.data.models.AnimeModel
import com.university_assignment.invididualtask.data.models.BriefAnimeModel
import com.university_assignment.invididualtask.data.models.SeasonAnimeModel

interface IAnimeRepository {
    /**
     * Get general anime information: title, description, rates, seasons count and so on
     */
    suspend fun getAnimeInfo(animeTitle: String): AnimeModel?

    /**
     * Get the information about the season, its episodes and title
     */
    suspend fun getSeasonInfo(animeTitle: String, seasonNumber: Int, isFilm: Boolean): SeasonAnimeModel?
}