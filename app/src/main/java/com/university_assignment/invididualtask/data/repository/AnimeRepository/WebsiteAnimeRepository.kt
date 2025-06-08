package com.university_assignment.invididualtask.data.repository.AnimeRepository

import com.university_assignment.invididualtask.api.interfaces.IParser
import com.university_assignment.invididualtask.api.interfaces.IWebClient
import com.university_assignment.invididualtask.data.models.AnimeModel
import com.university_assignment.invididualtask.data.models.SeasonAnimeModel
import com.university_assignment.invididualtask.data.repository.interfaces.IAnimeRepository
import com.university_assignment.invididualtask.utils.interfaces.IUrlHelper
import io.ktor.client.statement.bodyAsText
import jakarta.inject.Inject

class WebsiteAnimeRepository @Inject constructor(
    private val urlHelper: IUrlHelper,
    private val parser: IParser,
    private val webClient: IWebClient
) : IAnimeRepository {
    override suspend fun getAnimeInfo(animeTitle: String): AnimeModel? {
        val url = urlHelper.generateAnimeMainPageUrl(animeTitle)
        val bodyPage = getPageResponse(url)

        return parser.getAnimeInfo(bodyPage)
    }

    override suspend fun getSeasonInfo(animeTitle: String, seasonNumber: Int, isFilm: Boolean): SeasonAnimeModel? {
        val url = urlHelper.generateSeasonUrl(animeTitle, seasonNumber, isFilm)
        val bodyPage = getPageResponse(url)

        return parser.getSeasonInfo(bodyPage)
    }

    private suspend fun getPageResponse(pageUrl: String): String {
        pageCache[pageUrl]?.let { return it }

        val res = webClient.getWebsitePage(pageUrl)
        val body = res.bodyAsText()
        pageCache[pageUrl] = body
        return body
    }

    companion object {
        private val pageCache = mutableMapOf<String, String>()
    }
}