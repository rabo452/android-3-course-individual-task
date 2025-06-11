package com.university_assignment.invididualtask.data.repository.interfaces

import android.webkit.WebView
import com.university_assignment.invididualtask.data.models.videoHoster.VideoStreamManifest

interface IHosterStreamRepository {
    /**
     * Get the streaming video manifest which contains all required structure to launch a video
     * @param webView usually it requires to have some browser in order to get the stream parts,
     * so it can be propagated in those cases
     */
    suspend fun getEpisodeStreamManifest(url: String, webView: WebView? = null): VideoStreamManifest?
}