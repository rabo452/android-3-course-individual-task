package com.university_assignment.invididualtask.api.interfaces

import io.ktor.client.statement.HttpResponse

interface IWebClient {
    /**
     * get the page response from the website page
     */
    suspend fun getWebsitePage(url: String): HttpResponse

    /**
     * get the url from the streaming host platform
     */
    suspend fun getStreamUrl(url: String, headers: Map<String, String>): HttpResponse

    /**
     * Get the final redirected url in case of 300 codes
     */
    suspend fun getFinalRedirectedUrl(url: String): String
}