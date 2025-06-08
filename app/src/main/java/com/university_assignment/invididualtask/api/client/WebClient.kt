package com.university_assignment.invididualtask.api.client

import com.university_assignment.invididualtask.api.interfaces.IWebClient
import com.university_assignment.invididualtask.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import jakarta.inject.Inject

class WebClient @Inject constructor() : IWebClient {
    private val client = HttpClient(CIO) {
        followRedirects = true
    }

    override suspend fun getWebsitePage(url: String): HttpResponse {
        return client.get(url) {
            headers {
                append("User-Agent", Constants.REQUEST_USER_AGENT)
                append("Referer", "https://${Constants.SITE_ROOT}/")
            }
        }
    }
}