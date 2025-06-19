package com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.SharedAnimeSeasonViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.anime.SeasonAnimeModel
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel.SharedEpisodeViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
import com.university_assignment.invididualtask.ui.theme.localThemeColors
import com.university_assignment.invididualtask.utils.NavScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun AnimeSeasonScreen(
    navController: NavController,
    seasonViewModel: SharedAnimeSeasonViewModel,
    episodeViewModel: SharedEpisodeViewModel,
    animeRepositoryViewModel: AnimeRepositoryViewModel = hiltViewModel()
) {
    val theme = localThemeColors.current
    val title by seasonViewModel.animeTitle.collectAsState()
    val seasonNumber by seasonViewModel.seasonNumber.collectAsState()
    val currentEpisode by seasonViewModel.episodeNumber.collectAsState()
    val isFilm by seasonViewModel.isFilm.collectAsState()
    val animeRepository = animeRepositoryViewModel.repository
    var isPageLoaded by remember {
        mutableStateOf(false)
    }
    var seasonInfo by remember {
        mutableStateOf<SeasonAnimeModel?>(null)
    }
    var animeInfo by remember {
        mutableStateOf<AnimeModel?>(null)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            seasonInfo = animeRepository.getSeasonInfo(title!!, seasonNumber!!, isFilm)
            animeInfo = animeRepository.getAnimeInfo(title!!)

            isPageLoaded = true
        }
    }

    if (!isPageLoaded) {
        Text("loading...")
        return
    }

    // check if the information about anime is loaded correctly
    if (!arrayOf(seasonInfo, animeInfo).all { el -> el != null }) {
        Text("Some error happened")
        return
    }

    val _seasonInfo = seasonInfo!!
    val _animeInfo = animeInfo!!
    val verticalScrollState = rememberScrollState()
    val currentSeasonNumber = _seasonInfo.seasonNumber
    var seasonsCount = _animeInfo.seasonsCount
    val currentEpisodeCount = _seasonInfo.episodes.size

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(verticalScrollState),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp).padding(top=15.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.height(225.dp),
                model = _animeInfo.thumbnailUrl,
                contentDescription = null,
            )

            Text(
                _animeInfo.title,
                modifier = Modifier.padding(top=15.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Text(
                _animeInfo.description,
                modifier = Modifier.padding(top=15.dp),
                textAlign = TextAlign.Start
            )
        }

        Column(
            modifier = Modifier.padding(top=10.dp).fillMaxWidth().background(theme.seasonPageBackgroundColor).padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Seasons:", modifier = Modifier.padding(top = 15.dp), color=Color(255, 255, 255))
            FlowRow(modifier = Modifier.padding(top=15.dp)) {
                if (_animeInfo.isFilmAvailable) {
                    Column(
                        modifier = Modifier
                            .size(80.dp, 40.dp)
                            .background(if (isFilm) theme.selectedSeasonColor else theme.unselectedColor)
                            .clickable {
                                seasonViewModel.updateSeasonNumber(-1)
                                seasonViewModel.updateEpisodeNumber(1)
                                seasonViewModel.updateTitle(title!!)
                                seasonViewModel.updateIsFilm(true)
                                navController.navigate(NavScreen.SeasonPage.pageName)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Films", color = Color(255, 255, 255))
                    }
                }

                for (seasonNumber in 1..seasonsCount) {
                    Column(
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .background(if (seasonNumber == currentSeasonNumber) theme.selectedSeasonColor else theme.unselectedColor)
                            .clickable {
                                seasonViewModel.updateSeasonNumber(seasonNumber)
                                seasonViewModel.updateEpisodeNumber(1)
                                seasonViewModel.updateTitle(title!!)
                                seasonViewModel.updateIsFilm(false)
                                navController.navigate(NavScreen.SeasonPage.pageName)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(seasonNumber.toString(), color = Color(255, 255, 255))
                    }
                }
            }

            Text("Episodes:", modifier = Modifier.padding(top = 15.dp), color=Color(255, 255, 255))
            FlowRow(modifier = Modifier.padding(top=15.dp)) {
                for (episodeNumber in 1..currentEpisodeCount) {
                    Column(
                        modifier = Modifier
                            .size(40.dp, 40.dp)
                            .background(if (currentEpisode == episodeNumber) theme.selectedSeasonColor else theme.unselectedColor)
                            .clickable {
                                episodeViewModel.updateIsFilm(isFilm)
                                episodeViewModel.updateSeasonNumber(currentSeasonNumber)
                                episodeViewModel.updateEpisodeNumber(episodeNumber)
                                seasonViewModel.updateEpisodeNumber(episodeNumber)
                                episodeViewModel.updateAnimeInfo(animeInfo!!)
                                navController.navigate(NavScreen.EpisodePage.pageName)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(episodeNumber.toString(), color = Color(255, 255, 255))
                    }
                }
            }
        }
    }
}