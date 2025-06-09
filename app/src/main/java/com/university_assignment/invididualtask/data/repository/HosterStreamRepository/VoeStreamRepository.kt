package com.university_assignment.invididualtask.data.repository.HosterStreamRepository

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.university_assignment.invididualtask.data.models.EpisodeStreamManifest
import com.university_assignment.invididualtask.data.repository.interfaces.IHosterStreamRepository
import kotlinx.coroutines.CompletableDeferred

class VoeStreamRepository: IHosterStreamRepository {
    @SuppressLint("SetJavaScriptEnabled")
    override suspend fun getEpisodeStreamManifest(url: String, webView: WebView?): EpisodeStreamManifest? {
        // without web view it's not possible to get stream parts
        webView ?: return null

        val streamsDeferred = CompletableDeferred<EpisodeStreamManifest?>()
        val manifestFileName = "index-f1-v1-a1.m3u8"
        val jsCode = """
            interval = setInterval(() => {
                let element = document.querySelector('.spin');
                    if (element) {
                        element.click();
                        clearInterval(interval);
                    }
            }, 100)
        """.trimIndent()

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                    if (request?.url.toString().contains(manifestFileName)) {
                        val headers = request?.requestHeaders?.toMap()
                        streamsDeferred.complete(EpisodeStreamManifest(request!!.url.toString(), headers!!))
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

        return streamsDeferred.await()
    }
}