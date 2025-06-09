package com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.university_assignment.invididualtask.api.client.WebClient
import com.university_assignment.invididualtask.data.repository.HosterStreamRepository.VoeStreamRepository
import com.university_assignment.invididualtask.ui.screens.AnimeEpisodeScreen.viewModel.VideoEpisodeViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.AnimeRepositoryViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(UnstableApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
@Preview
fun AnimeEpisodeScreen(repositoryViewModel: AnimeRepositoryViewModel = hiltViewModel()) {

}

//    val context = LocalContext.current
//    val webView = remember { WebView(context) }
//
//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.Main) {
//            val url = "https://jilliandescribecompany.com/e/lerfn10altod"
//            VoeStreamRepository().getEpisodeStreamManifest(url, webView)
//        }
//    }
//
//    val url = "https://cdn-kuc3ehpz4wnczfyz.orbitcache.com/engine/hls2-c/01/14255/lerfn10altod_,n,l,.urlset/index-f1-v1-a1.m3u8?t=W6lHOUM-y9ZZz-fwICfIyEuRCl2q1bKdOlJOii00WSQ&s=1749462617&e=14400&f=71277316&node=5C9ec2IewNduoiQvOtzmwsCxpanErTGVF2tEZwSD46k=&i=0.1&sp=2500&asn=6876&q=n,l&rq=JiUrv6wVfYbSmRyZZtvKY6GMIA2kZkHRCX6U9ihG"
//    val url2 = "https://be6721.rcr72.waw04.cdn255.com/hls2/02/08860/pmt0dlf8s2du_h/index-v1-a1.m3u8?t=H9HaMpVSWPpzq2iojU2BbzO-ZU2YYJAqcU2PfddT4b8&s=1749465395&e=10800&f=44303273&srv=29&asn=6876&sp=5500&p="
//    val exoPlayer = remember {
//        val dataSourceFactory = DefaultHttpDataSource
//            .Factory()
//            .setDefaultRequestProperties()
//        val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(MediaItem.fromUri(url2))
//
//        ExoPlayer.Builder(context).build().apply {
//            setMediaSource(mediaSource)
//            prepare()
//            playWhenReady = false
//        }
//    }
//
//    Column {
//        AndroidView(
//            factory = { webView },
//            modifier = Modifier.size(0.dp).alpha(0f)
//        )
//
//        AndroidView(
//            factory = {PlayerView(context).apply {
//                player = exoPlayer
//            }},
//            modifier = Modifier.fillMaxSize()
//        )
//    }