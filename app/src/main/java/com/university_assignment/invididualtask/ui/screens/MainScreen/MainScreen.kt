package com.university_assignment.invididualtask.ui.screens.MainScreen

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.university_assignment.invididualtask.api.client.WebClient
import com.university_assignment.invididualtask.data.models.anime.BriefAnimeModel
import com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel.SharedAnimeSeasonViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
import com.university_assignment.invididualtask.utils.NavScreen
import io.ktor.client.statement.bodyAsBytes
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.text.Normalizer


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MainScreen(
    navController: NavController,
    seasonViewModel: SharedAnimeSeasonViewModel,
    animeRepositoryViewModel: AnimeRepositoryViewModel = hiltViewModel()
) {
    val repository = animeRepositoryViewModel.repository
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val res = repository.getAnimeMainInfo()
            res
        }
    }

//    val briefAnime = BriefAnimeModel(
//        "attack on titan",
//        "https://aniworld.to/public/img/cover/attack-on-titan-stream-cover-gy1YrBf3uQ8k2I0aAXK9TggID2Kn7Koz_150x225.jpg"
//    )
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = {
//            seasonViewModel.updateSeasonNumber(1)
//            seasonViewModel.updateTitle(briefAnime.title)
//            seasonViewModel.updateIsFilm(false)
//            navController.navigate(NavScreen.SeasonPage.pageName)
//        }) {
//            Text("click on me")
//        }
//    }
}