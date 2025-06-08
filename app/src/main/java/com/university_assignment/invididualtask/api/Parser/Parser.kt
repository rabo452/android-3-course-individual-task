package com.university_assignment.invididualtask.api.Parser

import com.university_assignment.invididualtask.api.interfaces.IParser
import com.university_assignment.invididualtask.data.models.AnimeModel
import com.university_assignment.invididualtask.data.models.EpisodeLanguage
import com.university_assignment.invididualtask.data.models.EpisodeModel
import com.university_assignment.invididualtask.data.models.SeasonAnimeModel
import com.university_assignment.invididualtask.data.models.VideoHoster
import com.university_assignment.invididualtask.utils.Constants
import jakarta.inject.Inject
import org.jsoup.Jsoup

class Parser @Inject constructor() : IParser {
    override fun getAnimeInfo(pageBody: String): AnimeModel? {
        val doc = Jsoup.parse(pageBody)

        val imageBlock = doc.select(".seriesCoverBox > img")?.first()
        val titleBlock = doc.select(".series-title h1")?.first()
        val descriptionBlock = doc.select(".seri_des")?.first()
        val seasonBlocks = doc.select(".hosterSiteDirectNav > ul:nth-child(1) > li")

        val allExist = arrayOf(imageBlock, titleBlock, descriptionBlock, seasonBlocks).all { el -> el != null }
        return if(allExist) AnimeModel(
            title = titleBlock!!.text(),
            thumbnailUrl = "https://${Constants.SITE_ROOT}${imageBlock!!.attr("data-src")}",
            description = descriptionBlock!!.text(),
            seasonsCount = seasonBlocks!!.count() - 1
        ) else null
    }

    override fun getSeasonInfo(pageBody: String): SeasonAnimeModel? {
        val doc = Jsoup.parse(pageBody)

        val seasonBlock = doc.select(".active")?.first()
        val episodeBlocks = doc.select(".seasonEpisodesList tbody tr")

        val allExist = arrayOf(seasonBlock, episodeBlocks).all { el -> el != null }
        if (!allExist) return null

        val seasonTitle = seasonBlock!!.attr("title").toString()
        val isFilm = seasonTitle.contains("Alle Filme")
        val seasonNumberRegex = Regex("""Staffel (\d+)""")
        val seasonNumber = if (isFilm) 0 else seasonNumberRegex.find(seasonTitle)!!.groups[1].toString().toInt()

        val episodes: Array<EpisodeModel> = episodeBlocks.mapIndexed { episodeCount, episodeBlock ->
            val episodeNumber = episodeCount + 1
            val title = episodeBlock.select(".seasonEpisodeTitle a")?.first()?.text()
            val languageBlocks = episodeBlock.select(".editFunctions img.flag")
            val hosterBlocks = episodeBlock.select("td:nth-child(3) i")

            val hosters: Array<VideoHoster> = hosterBlocks.map { hosterBlock ->
                val hosterTitle = hosterBlock.attr("title").toString()
                when (hosterTitle) {
                    "VOE" -> VideoHoster.VOE
                    "Filemoon" -> VideoHoster.FileMoon
                    "LoadX" -> VideoHoster.LoadX
                    "Vidmoly" -> VideoHoster.Vidmoly
                    "Luluvdo" -> VideoHoster.Luluvdo
                    else -> throw Error("unknown hoster: $hosterTitle for the anime: $seasonTitle episode $episodeNumber")
                }
            }.toTypedArray()

            val languages: Array<EpisodeLanguage> = languageBlocks.map { langBlock ->
                val langTitle = langBlock.attr("title").toString()
                when (langTitle) {
                    "Deutsch/German" -> EpisodeLanguage.DE_VOICE
                    "Mit deutschem Untertitel" -> EpisodeLanguage.DE_SUBTITLE
                    "Englisch" -> EpisodeLanguage.EN_SUBTITLE
                    else -> throw Error("unknown language: $langTitle for the anime: $seasonTitle episode $episodeNumber")
                }
            }.toTypedArray()

            EpisodeModel(
                title = title!!,
                number = episodeNumber,
                languages = languages,
                videoHosters = hosters
            )
        }.toTypedArray()

        return SeasonAnimeModel(
            episodes = episodes,
            seasonNumber = seasonNumber,
            isFilm = isFilm
        )
    }
}