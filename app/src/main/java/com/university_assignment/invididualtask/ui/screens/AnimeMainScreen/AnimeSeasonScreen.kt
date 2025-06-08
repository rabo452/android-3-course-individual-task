package com.university_assignment.invididualtask.ui.screens.AnimeMainScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.university_assignment.invididualtask.ui.screens.AnimeMainScreen.ViewModels.RepositoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
@Preview
fun AnimeSeasonScreen(viewModel: RepositoryViewModel = hiltViewModel()) {
    val repository = viewModel.repository

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val result = repository.getAnimeInfo("attack on titan")
            println(result)
        }
    }

    Text("example", fontSize = 12.sp)
}