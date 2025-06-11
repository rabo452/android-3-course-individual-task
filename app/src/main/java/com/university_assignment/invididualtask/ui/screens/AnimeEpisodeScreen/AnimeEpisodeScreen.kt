package com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel.SharedEpisodeViewModel
import com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel.SharedVideoViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import com.university_assignment.invididualtask.utils.NavScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AnimeEpisodeScreen(
    navController: NavController,
    episodeViewModel: SharedEpisodeViewModel,
    videoViewModel: SharedVideoViewModel,
    repositoryViewModel: AnimeRepositoryViewModel = hiltViewModel()
) {
    val animeInfo by episodeViewModel.animeInfo.collectAsState()
    val seasonNumber by episodeViewModel.seasonNumber.collectAsState()
    val episodeNumber by episodeViewModel.episodeNumber.collectAsState()
    val isFilm by episodeViewModel.isFilm.collectAsState()
    val animeRepository = repositoryViewModel.repository
    var episodeHostStreams by remember {
        mutableStateOf<List<EpisodeStreamInfo>?>(null)
    }
    var isLoaded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            episodeHostStreams = animeRepository.getEpisodeInfo(
                animeInfo!!.title,
                seasonNumber!!,
                episodeNumber!!,
                isFilm!!
            )
            isLoaded = true
        }
    }

    if (!isLoaded) {
        Text("loading...")
        return
    }

    if (episodeHostStreams == null) {
        Text("some error happened")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (episodeHostStream in episodeHostStreams) {
            Button(onClick = {
                videoViewModel.updateVideoInfo(episodeHostStream)
                navController.navigate(NavScreen.VideoPage.pageName)
            }) {
                Text("lang: ${episodeHostStream.language}, hoster: ${episodeHostStream.videoHoster}")
            }
        }
    }
}
