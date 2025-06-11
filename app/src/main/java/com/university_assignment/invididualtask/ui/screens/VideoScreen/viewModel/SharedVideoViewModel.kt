package com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class SharedVideoViewModel @Inject constructor() : ViewModel() {
    var videoInfo = MutableStateFlow<EpisodeStreamInfo?>(null)

    fun updateVideoInfo(_videoInfo: EpisodeStreamInfo) {
        videoInfo.value = _videoInfo
    }
}