package com.university_assignment.invididualtask.ui.screens.VideoScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel.PlayerViewModel

@Composable
fun VideoComponent(playerViewModel: PlayerViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.background(Color(0, 0, 0)).fillMaxSize()
    ) {
        AndroidView(
            factory = { PlayerView(context).apply { player = playerViewModel.exoPlayer.value } },
            modifier = Modifier.fillMaxSize()
        )
    }
}