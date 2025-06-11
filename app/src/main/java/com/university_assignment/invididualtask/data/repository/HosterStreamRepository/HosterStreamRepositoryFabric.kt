package com.university_assignment.invididualtask.data.repository.HosterStreamRepository

import com.university_assignment.invididualtask.api.interfaces.IWebClient
import com.university_assignment.invididualtask.data.models.videoHoster.VideoHoster
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepository
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepositoryFabric
import javax.inject.Inject


class HosterStreamRepositoryFabric @Inject constructor(val webClient: IWebClient) : IHosterStreamRepositoryFabric {
    override fun createHosterStreamRepositoryInstance(hoster: VideoHoster): IHosterStreamRepository {
        return when (hoster) {
            VideoHoster.VOE -> VoeStreamRepository(webClient)
            VideoHoster.LoadX -> TODO()
            VideoHoster.FileMoon -> TODO()
            VideoHoster.Luluvdo -> TODO()
            VideoHoster.Vidmoly -> TODO()
            VideoHoster.DoodStream -> TODO()
        }
    }
}