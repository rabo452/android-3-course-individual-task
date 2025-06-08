package com.university_assignment.invididualtask.ui.screens.AnimeMainScreen.ViewModels

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.repository.interfaces.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    val repository: IAnimeRepository
) : ViewModel()