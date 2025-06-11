package com.university_assignment.invididualtask.ui.screens.MainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.SharedAnimeSeasonViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.university_assignment.invididualtask.data.models.anime.BriefAnimeModel
import com.university_assignment.invididualtask.data.models.anime.HomeAnimeModel
import com.university_assignment.invididualtask.ui.shared.components.AnimeCardList
import com.university_assignment.invididualtask.utils.NavScreen

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MainScreen(
    navController: NavController,
    seasonViewModel: SharedAnimeSeasonViewModel,
    animeRepositoryViewModel: AnimeRepositoryViewModel = hiltViewModel()
) {
    val repository = animeRepositoryViewModel.repository
    var mainPageInfo by remember {
        mutableStateOf<HomeAnimeModel?>(null)
    }
    var isLoaded by remember {
        mutableStateOf(false)
    }

    // load the animes from the repository
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainPageInfo = repository.getAnimeMainInfo()
            isLoaded = true
        }
    }

    if (!isLoaded) {
        Text("loading...")
        return
    }

    if (mainPageInfo == null) {
        Text("Some error happened")
        return
    }

    // remove null checkers
    val _mainPageInfo = mainPageInfo!!

    val verticalScrollState = rememberScrollState()
    val onCardSelectCallback: (anime: BriefAnimeModel) -> Unit = { anime ->
        seasonViewModel.updateSeasonNumber(1)
        seasonViewModel.updateTitle(anime.title)
        seasonViewModel.updateIsFilm(false)
        navController.navigate(NavScreen.SeasonPage.pageName)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
            .verticalScroll(verticalScrollState)
    ) {
        Text("Recommended:",
            modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        AnimeCardList(_mainPageInfo.animeRecommendation.toList()) { anime -> onCardSelectCallback(anime) }

        Text("New:",
            modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        AnimeCardList(_mainPageInfo.newAnimes.toList()) { anime -> onCardSelectCallback(anime) }

        Text("Popular:",
            modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        AnimeCardList(_mainPageInfo.likedAnimes.toList()) { anime -> onCardSelectCallback(anime) }
    }
}