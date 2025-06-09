package com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.models.EpisodeStreamManifest
import kotlinx.coroutines.flow.MutableStateFlow

class VideoEpisodeViewModel : ViewModel() {
    var videoStreamManifest = MutableStateFlow<EpisodeStreamManifest?>(null)
}