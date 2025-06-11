package com.university_assignment.invididualtask.utils.interfaces

interface IUrlHelper {

    fun generateMainPageUrl(): String
    /**
     * Generate the url of main page (page of the first season)
     */
    fun generateAnimeMainPageUrl(animeTitle: String): String

    /**
     * Generate the url for website season page
     */
    fun generateSeasonUrl(animeTitle: String, seasonNumber: Int, isFilm: Boolean): String

    /**
     * Generate the url for the website episode page
     */
    fun generateEpisodeUrl(animeTitle: String, seasonNumber: Int, episodeNumber: Int, isFilm: Boolean): String
}