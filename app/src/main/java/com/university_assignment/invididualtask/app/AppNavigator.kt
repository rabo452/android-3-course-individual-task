package com.university_assignment.invididualtask.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.AnimeSeasonScreen
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.AnimeSeasonViewModel
import com.university_assignment.invididualtask.ui.screens.MainScreen.MainScreen
import com.university_assignment.invididualtask.utils.NavScreen


@Composable
fun AppNavigator(
    seasonViewModel: AnimeSeasonViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.MainPage.pageName) {
        composable(NavScreen.MainPage.pageName) {
            MainScreen(navController, seasonViewModel)
        }
        composable(NavScreen.SeasonPage.pageName) {
            AnimeSeasonScreen(navController, seasonViewModel)
        }
    }
}