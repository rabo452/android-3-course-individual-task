package com.university_assignment.invididualtask.ui.screens.VideoScreen

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel.SharedVideoViewModel
import com.university_assignment.invididualtask.ui.shared.viewModels.HostRepositoryFabricViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.university_assignment.invididualtask.data.models.videoHoster.VideoStreamManifest
import com.university_assignment.invididualtask.ui.screens.VideoScreen.viewModel.PlayerViewModel
import com.university_assignment.invididualtask.utils.NavScreen
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.currentBackStackEntryAsState
import com.university_assignment.invididualtask.ui.screens.VideoScreen.components.VideoComponent

@SuppressLint("ViewModelConstructorInComposable", "StateFlowValueCalledInComposition",
    "UnrememberedGetBackStackEntry"
)
@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    navController: NavController,
    videoViewModel: SharedVideoViewModel,
    hostRepositoryFabricViewModel: HostRepositoryFabricViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val hostRepositoryFabric = hostRepositoryFabricViewModel.repository
    val videoStream by videoViewModel.videoInfo.collectAsState()
    val hostRepository = hostRepositoryFabric.createHosterStreamRepositoryInstance(videoStream!!.videoHoster)
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    var loaded by remember { mutableStateOf(false) }
    var episodeManifest by remember { mutableStateOf<VideoStreamManifest?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    DisposableEffect(Unit) {
        onDispose {
            val currentPage = navBackStackEntry?.destination?.route
            // check whether it's the configuration changes or a user goes back to the previous page
            if (currentPage == NavScreen.VideoPage.pageName) {
                // configuration change - do nothing
            } else {
                // if the a user goes back, release the player
                playerViewModel.exoPlayer.value?.release()
                playerViewModel.updateExoPlayer(null)
            }
        }
    }

    // if the player object exist by this far, there were configuration changes (phone flipping)
    if (playerViewModel.exoPlayer.value != null) {
        VideoComponent(playerViewModel)
        return
    } else {
        LaunchedEffect(Unit) {
            episodeManifest = hostRepository.getEpisodeStreamManifest(videoStream!!.url, webView)
            loaded = true
        }
    }


    if (!loaded) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { webView },
                modifier = Modifier.size(0.dp)
            )
            Text("loading...")
        }
        return
    }

    // clear web view
    webView.destroy()

    if (episodeManifest == null) {
        Text("some error happened")
        return
    }

    val manifestUrl = episodeManifest!!.sourceUrl
    val requestHeaders = episodeManifest!!.httpHeaders

    val exoPlayer: ExoPlayer by lazy {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setDefaultRequestProperties(requestHeaders)
        val mediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(manifestUrl))

        ExoPlayer.Builder(context).build().apply {
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = false
        }
    }

    // it's required to save the instance of the exo player in case of config changes like phone flipping
    playerViewModel.updateExoPlayer(exoPlayer)
    VideoComponent(playerViewModel)
}

