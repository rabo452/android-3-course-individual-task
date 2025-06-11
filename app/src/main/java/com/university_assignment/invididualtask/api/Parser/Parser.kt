package com.university_assignment.invididualtask.api.Parser

import com.university_assignment.invididualtask.api.interfaces.IParser
import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.anime.BriefAnimeModel
import com.university_assignment.invididualtask.data.models.anime.EpisodeLanguage
import com.university_assignment.invididualtask.data.models.anime.EpisodeModel
import com.university_assignment.invididualtask.data.models.anime.HomeAnimeModel
import com.university_assignment.invididualtask.data.models.anime.NewEpisode
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import com.university_assignment.invididualtask.data.models.anime.SeasonAnimeModel
import com.university_assignment.invididualtask.data.models.videoHoster.VideoHoster
import com.university_assignment.invididualtask.utils.Constants
import jakarta.inject.Inject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class Parser @Inject constructor() : IParser {
    override fun getMainPageInfo(pageBody: String): HomeAnimeModel? {
        val doc = Jsoup.parse(pageBody)
        // regex for getting season number and episode from the text
        val newEpisodeTextRegex = Regex("""S(\d{2})\s*E(\d{2})""")

        val parseFunc: (block: Element) -> BriefAnimeModel = { block ->
            val title = (block.select("h3")?.first()?.text())!!
            val thumbnailUrl = "https://${Constants.SITE_ROOT}${block.select("img").attr("data-src")}"

            BriefAnimeModel(title, thumbnailUrl)
        }

        val recommendedAnimeBlocks = doc.select(".carousel")?.first()?.select(".coverListItem a") ?: return null
        val newAnimeBlocks = doc.select(".carousel")?.get(2)?.select(".coverListItem a") ?: return null
        val likedAnimeBlocks = doc.select(".carousel")?.get(3)?.select(".coverListItem a") ?: return null
        val newEpisodeBlocks = doc.select(".newEpisodeList .row > .col-md-12") ?: return null

        val recommendedAnimes = recommendedAnimeBlocks.map(parseFunc).toTypedArray()
        val newAnimes = newAnimeBlocks.map(parseFunc).toTypedArray()
        val likedAnimes = likedAnimeBlocks.map(parseFunc).toTypedArray()
        val newEpisodes = newEpisodeBlocks.map { block ->
            val time = (block.select(".elementFloatRight")?.first()?.text())!!
            val episodeText = block.select(".listTag")?.first()?.text()
            val title = block.select("strong")?.first()?.text()
            val episodeLangImgSrc = (block.select("img.flag")?.get(1)?.attr("src"))!!
            val episodeLang = when {
                episodeLangImgSrc.contains("japanese-german") -> EpisodeLanguage.DE_SUBTITLE
                episodeLangImgSrc.contains("german") -> EpisodeLanguage.DE_VOICE
                episodeLangImgSrc.contains("english") -> EpisodeLanguage.EN_SUBTITLE
                else -> throw Error("no appropriate language found for the this image: $episodeLangImgSrc")
            }

            val match = newEpisodeTextRegex.find(episodeText!!)!!
            val (season, episode) = match.destructured
            NewEpisode(
                title!!,
                time,
                episodeLang,
                episode!!.toInt(),
                season!!.toInt()
            )
        }.toTypedArray()

        return HomeAnimeModel(recommendedAnimes, newEpisodes, newAnimes, likedAnimes)
    }

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
        val seasonNumber = if (isFilm) 0 else seasonNumberRegex.find(seasonTitle)?.groups[1]!!.value.toInt()

        val episodes: Array<EpisodeModel> = episodeBlocks.mapIndexed { episodeCount, episodeBlock ->
            val episodeNumber = episodeCount + 1
            val title = episodeBlock.select(".seasonEpisodeTitle a")?.first()?.text()
            val languageBlocks = episodeBlock.select(".editFunctions img.flag")
            val hosterBlocks = episodeBlock.select("td:nth-child(3) i")

            val hosters: Array<VideoHoster> = hosterBlocks.map { hosterBlock ->
                val hosterTitle = hosterBlock.attr("title").toString()
                hostTitleToClass(hosterTitle)
                    ?: throw Error("unknown hoster: $hosterTitle for the anime: $seasonTitle episode $episodeNumber")
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

    override fun getEpisodeStreamInfo(pageBody: String): List<EpisodeStreamInfo>? {
        val doc = Jsoup.parse(pageBody)

        val hostStreamBlocks = doc.select("li[data-lang-key]") ?: return null

        return hostStreamBlocks.map { el ->
            val link = "https://${Constants.SITE_ROOT}${el.attr("data-link-target")}"
            val langId = el.attr("data-lang-key")
            val lang = when(langId) {
                "1" -> EpisodeLanguage.DE_VOICE
                "2" -> EpisodeLanguage.EN_SUBTITLE
                "3" -> EpisodeLanguage.DE_SUBTITLE
                else -> throw Error("unknown language id: $langId")
            }
            val iconTitle = el.select("i")?.attr("title")
            val host = hostTitleToClass(iconTitle!!) ?:
                throw Error("unknown host: $iconTitle")

            EpisodeStreamInfo(link, host, lang)
        }
    }

    private fun hostTitleToClass(hostTitle: String): VideoHoster? {
        val hostToClasses = mapOf<String, VideoHoster>(
            "VOE" to VideoHoster.VOE,
            "Filemoon" to VideoHoster.FileMoon,
            "LoadX" to VideoHoster.LoadX,
            "Vidmoly" to VideoHoster.Vidmoly,
            "Luluvdo" to VideoHoster.Luluvdo,
            "Doodstream" to VideoHoster.DoodStream,
        )

        for ((hostName, hostClass) in hostToClasses) {
            if (hostTitle.lowercase().contains(hostName.lowercase())) {
                return hostClass
            }
        }

        return null
    }
}