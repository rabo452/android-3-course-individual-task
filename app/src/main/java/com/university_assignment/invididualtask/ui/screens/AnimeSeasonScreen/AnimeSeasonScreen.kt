package com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.AnimeSeasonViewModel
import androidx.compose.runtime.getValue


@Composable
fun AnimeSeasonScreen(navController: NavController, seasonViewModel: AnimeSeasonViewModel) {
    val animeTitle by seasonViewModel.animeTitle.collectAsState()

    Button(onClick = {
        seasonViewModel.updateTitle("new title")
    }) {
        Text("some title: $animeTitle", fontSize = 12.sp)
    }

}