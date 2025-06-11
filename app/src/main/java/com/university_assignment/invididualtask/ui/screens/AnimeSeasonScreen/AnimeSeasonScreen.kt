package com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.anime.SeasonAnimeModel
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel.SharedEpisodeViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
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
    val title by seasonViewModel.animeTitle.collectAsState()
    val seasonNumber by seasonViewModel.seasonNumber.collectAsState()
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val seasonNumber = seasonInfo!!.seasonNumber
        val isFilm = seasonInfo!!.isFilm
        for (episode in seasonInfo!!.episodes) {
            Button(onClick = {
                episodeViewModel.updateIsFilm(isFilm)
                episodeViewModel.updateSeasonNumber(seasonNumber)
                episodeViewModel.updateEpisodeNumber(episode.number)
                episodeViewModel.updateAnimeInfo(animeInfo!!)
                navController.navigate(NavScreen.EpisodePage.pageName)
            }) {
                Text("show ${episode.title}, episode number: ${episode.number}")
            }
        }
    }
}

