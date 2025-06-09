package com.university_assignment.invididualtask.ui.screens.MainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.AnimeSeasonViewModel
import com.university_assignment.invididualtask.utils.NavScreen

@Composable
fun MainScreen(navController: NavController, seasonViewModel: AnimeSeasonViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Button(onClick = {
            seasonViewModel.updateTitle("cool title")
            navController.navigate(NavScreen.SeasonPage.pageName)
        }) {
            Text("click on me")
        }
    }
}