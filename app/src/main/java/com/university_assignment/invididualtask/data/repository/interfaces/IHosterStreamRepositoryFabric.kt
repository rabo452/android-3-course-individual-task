package com.university_assignment.invididualtask.data.repository.interfaces

import com.university_assignment.invididualtask.data.models.videoHoster.VideoHoster

interface IHosterStreamRepositoryFabric {
    /**
     * Get the instance of the hoster stream repository depending on the hoster
     */
    fun createHosterStreamRepositoryInstance(hoster: VideoHoster) : IHosterStreamRepository
}