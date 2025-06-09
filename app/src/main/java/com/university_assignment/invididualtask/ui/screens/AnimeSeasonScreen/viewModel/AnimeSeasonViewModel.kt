package com.university_assignment.invididualtask.ui.screens.AnimeSeasonScreen.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AnimeSeasonViewModel @Inject constructor() : ViewModel() {
    val animeTitle = MutableStateFlow<String?>(null)
    val animeSeason = MutableStateFlow<Int?>(null)
    val isFilm = MutableStateFlow<Boolean>(false)

    fun updateTitle(_animeTitle: String) {
        animeTitle.value = _animeTitle
    }
}