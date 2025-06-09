package com.university_assignment.invididualtask.ui.shared.viewModels

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.repository.interfaces.IAnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AnimeRepositoryViewModel @Inject constructor(
    val repository: IAnimeRepository
) : ViewModel()