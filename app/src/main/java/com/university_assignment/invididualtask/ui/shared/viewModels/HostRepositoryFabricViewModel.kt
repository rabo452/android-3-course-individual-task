package com.university_assignment.invididualtask.ui.shared.viewModels

import androidx.lifecycle.ViewModel
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepositoryFabric
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostRepositoryFabricViewModel @Inject constructor(
    val repository: IHosterStreamRepositoryFabric
) : ViewModel()