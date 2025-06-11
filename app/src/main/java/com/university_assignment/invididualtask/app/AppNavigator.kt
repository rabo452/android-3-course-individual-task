package com.university_assignment.invididualtask.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.AnimeEpisodeScreen
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel.SharedEpisodeViewModel
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.AnimeSeasonScreen
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.SharedAnimeSeasonViewModel
import com.university_assignment.invididualtask.ui.screens.MainScreen.MainScreen
import com.university_assignment.invididualtask.ui.screens.VideoScreen.VideoScreen
import com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel.SharedVideoViewModel
import com.university_assignment.invididualtask.utils.NavScreen


@Composable
fun AppNavigator(
    seasonViewModel: SharedAnimeSeasonViewModel = hiltViewModel(),
    episodeViewModel: SharedEpisodeViewModel = hiltViewModel(),
    videoViewModel: SharedVideoViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.MainPage.pageName) {
        composable(NavScreen.MainPage.pageName) {
            MainScreen(navController, seasonViewModel)
        }
        composable(NavScreen.SeasonPage.pageName) {
            AnimeSeasonScreen(navController, seasonViewModel, episodeViewModel)
        }
        composable(NavScreen.EpisodePage.pageName) {
            AnimeEpisodeScreen(navController, episodeViewModel, videoViewModel)
        }
        composable(NavScreen.VideoPage.pageName) {
            VideoScreen(navController, videoViewModel)
        }
    }
}