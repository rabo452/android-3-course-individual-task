package com.university_assignment.invididualtask.utils

import com.university_assignment.invididualtask.utils.interfaces.IUrlHelper
import jakarta.inject.Inject
import java.text.Normalizer

class UrlHelper @Inject constructor() : IUrlHelper {
    override fun generateMainPageUrl(): String = "https://${Constants.SITE_ROOT}"

    override fun generateAnimeMainPageUrl(animeTitle: String): String {
        return "https://${Constants.SITE_ROOT}/anime/stream/${animeTitle.convertAnimeTitle()}"
    }

    override fun generateSeasonUrl(animeTitle: String, seasonNumber: Int, isFilm: Boolean): String {
        return "https://${Constants.SITE_ROOT}/anime/stream/${animeTitle.convertAnimeTitle()}/" +
                if (isFilm) "filme" else "staffel-${seasonNumber}"
    }

    override fun generateEpisodeUrl(animeTitle: String, seasonNumber: Int, episodeNumber: Int, isFilm: Boolean): String {
        return "https://${Constants.SITE_ROOT}/anime/stream/${animeTitle.convertAnimeTitle()}/" +
                if (isFilm) "filme/film-${episodeNumber}" else "staffel-${seasonNumber}/episode-${episodeNumber}"
    }
}

fun String.convertAnimeTitle(): String {
    return this
        .replace(' ', '-')
        .replace(Regex("-{2,}"), "-")
        .normalizeToAscii()
        .removeNonAlphanumeric()
        .lowercase()
}

/**
 * Convert all text into the ascii characters
 */
fun String.normalizeToAscii(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}

/**
 * delete all characters which are not letters and non-numberic:
 * !, ?, :, and so on
 */
fun String.removeNonAlphanumeric(): String {
    return this.replace(Regex("[^A-Za-z0-9\\-]"), "")
}
