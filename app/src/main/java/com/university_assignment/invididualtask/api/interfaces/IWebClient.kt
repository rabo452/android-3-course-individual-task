package com.university_assignment.invididualtask.api.interfaces

import io.ktor.client.statement.HttpResponse

interface IWebClient {
    /**
     * get the page response from the website page
     */
    suspend fun getWebsitePage(url: String): HttpResponse
}