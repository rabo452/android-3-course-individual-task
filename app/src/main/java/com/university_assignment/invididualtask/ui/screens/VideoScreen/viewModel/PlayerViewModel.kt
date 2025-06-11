package com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {
    var exoPlayer = MutableStateFlow<ExoPlayer?>(null)

    fun updateExoPlayer(_player: ExoPlayer?) {
        exoPlayer.value = _player
    }
}