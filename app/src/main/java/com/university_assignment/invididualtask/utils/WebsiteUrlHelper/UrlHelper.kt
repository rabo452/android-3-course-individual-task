package com.university_assignment.invididualtask.utils.WebsiteUrlHelper

import com.university_assignment.invididualtask.utils.Constants
import com.university_assignment.invididualtask.utils.interfaces.IUrlHelper
import jakarta.inject.Inject

class UrlHelper @Inject constructor() : IUrlHelper {
    override fun generateAnimeMainPageUrl(animeTitle: String): String {
        return "https://${Constants.SITE_ROOT}/anime/stream" +
                "/${animeTitle.lowercase().replace(' ', '-')}"
    }

    override fun generateSeasonUrl(animeTitle: String, seasonNumber: Int, isFilm: Boolean): String {
        return "https://${Constants.SITE_ROOT}/anime/stream" +
                "/${animeTitle.lowercase().replace(' ', '-')}/" +
                if (isFilm) "filme" else "staffel-${seasonNumber}"
    }

    override fun generateEpisodeUrl(animeTitle: String, seasonNumber: Int, episodeNumber: Int, isFilm: Boolean): String {
        return "https://${Constants.SITE_ROOT}/anime/stream" +
                "/${animeTitle.lowercase().replace(' ', '-')}/" +
                if (isFilm) "filme/film-${episodeNumber}"
                else "staffel-${seasonNumber}/episode-${episodeNumber}"
    }
}