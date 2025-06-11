package com.university_assignment.invididualtask.data.repository.HosterStreamRepository

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.university_assignment.invididualtask.api.interfaces.IWebClient
import com.university_assignment.invididualtask.data.models.videoHoster.VideoStreamManifest
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepository
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoeStreamRepository(private val webClient: IWebClient): IHosterStreamRepository {
    @SuppressLint("SetJavaScriptEnabled")
    override suspend fun getEpisodeStreamManifest(url: String, webView: WebView?): VideoStreamManifest? {
        // without web view it's not possible to get stream parts
        webView ?: return null

        val manifestFileName = "v1-a1.m3u8"
        val linkRegex = Regex("window\\.location\\.href\\s*=\\s*'([^']+)'")
        val jsCode = """
            interval = setInterval(() => {
                let element = document.querySelector('.spin');
                    if (element) {
                        element.click();
                        clearInterval(interval);
                    }
            }, 100)
        """.trimIndent()
        // wait until all job is done and return the stream manifest
        val streamManifestDeferred = CompletableDeferred<VideoStreamManifest?>()

        withContext(Dispatchers.IO) {
            // by default, the url is given by /redirect url to the voe.sx site
            // in there the link to the real page so get it
            val res = webClient.getWebsitePage(url)
            val matchResult = linkRegex.find(res.bodyAsText())

            // now it's a real link to the hosting video, go to it by web view
            val url = matchResult?.groups?.get(1)?.value!!
            withContext(Dispatchers.Main) {
                webView.apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                            if (request?.url.toString().contains(manifestFileName)) {
                                val headers = request?.requestHeaders?.toMap()
                                streamManifestDeferred.complete(VideoStreamManifest(request!!.url.toString(), headers!!))
                            }
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            evaluateJavascript(jsCode) {}
                        }
                    }
                    loadUrl(url)
                }
            }
        }

        return streamManifestDeferred.await()
    }
}