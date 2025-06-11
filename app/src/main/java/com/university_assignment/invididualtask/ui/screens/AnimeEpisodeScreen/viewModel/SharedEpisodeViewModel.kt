package com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.models.anime.AnimeModel
import com.university_assignment.invididualtask.data.models.videoHoster.EpisodeStreamInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedEpisodeViewModel @Inject constructor() : ViewModel() {
    val animeInfo = MutableStateFlow<AnimeModel?>(null)
    val seasonNumber = MutableStateFlow<Int?>(null)
    val episodeNumber = MutableStateFlow<Int?>(null)
    val isFilm = MutableStateFlow<Boolean?>(null)

    fun updateAnimeInfo(_animeInfo: AnimeModel) {
        animeInfo.value = _animeInfo
    }

    fun updateSeasonNumber(_seasonNumber: Int) {
        seasonNumber.value = _seasonNumber
    }

    fun updateEpisodeNumber(_episodeNumber: Int) {
        episodeNumber.value = _episodeNumber
    }

    fun updateIsFilm(_isFilm: Boolean) {
        isFilm.value = _isFilm
    }
}