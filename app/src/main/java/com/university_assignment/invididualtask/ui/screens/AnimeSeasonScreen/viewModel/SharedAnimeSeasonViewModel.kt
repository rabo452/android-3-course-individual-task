package com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class SharedAnimeSeasonViewModel @Inject constructor() : ViewModel() {
    val animeTitle = MutableStateFlow<String?>(null)
    val seasonNumber = MutableStateFlow<Int?>(null)
    val episodeNumber = MutableStateFlow<Int?>(null)
    val isFilm = MutableStateFlow<Boolean>(false)

    fun updateTitle(_animeTitle: String) {
        animeTitle.value = _animeTitle
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