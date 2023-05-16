package com.thewire.wenlaunch.presentation.components.media


import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.thewire.wenlaunch.presentation.findActivity

private const val TAG = "VIDEO_PLAYER"

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String?,
    videoSeconds: MutableState<Float>,
    videoState: MutableState<String>,
    startFullScreen: Boolean = false,
    fullScreenCallback: () -> Unit
) {
    if (startFullScreen) {
        val activity = LocalContext.current.findActivity()
        val systemUiController: SystemUiController = rememberSystemUiController()
        LaunchedEffect(Unit) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            systemUiController.isSystemBarsVisible = false
        }
        DisposableEffect(Unit) {
            onDispose {
                systemUiController.isSystemBarsVisible = true
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
    BackHandler(startFullScreen) {
        fullScreenCallback()
    }

    val videoId = if(videoUri.isNullOrEmpty()) {
        null
    } else {
        getYoutubeVideoId(videoUri)
    }
    if (videoId.isNullOrEmpty()) {
        modifier.background(color = Color.Black)
        Box(
            modifier = modifier
        )

    } else {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        Box(
            modifier = modifier
        ) {
            AndroidView(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight()
                    .aspectRatio(1.78f),
                factory = { context ->
                    val player = YouTubePlayerView(context)
                    player.enableAutomaticInitialization = false
                    val options: IFramePlayerOptions =
                        IFramePlayerOptions.Builder().controls(0).build()
                    lifecycle.addObserver(player)
                    player.initialize(
                        getPlayerListener(
                            player,
                            videoId,
                            videoSeconds,
                            videoState,
                            fullScreenCallback,
                        ), options
                    )
                    player
                }
            )
        }

    }
}

fun getPlayerListener(
    playerView: YouTubePlayerView,
    videoId: String,
    videoSeconds: MutableState<Float>,
    videoState: MutableState<String>,
    fullScreenCallback: () -> Unit,
): YouTubePlayerListener {

    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {

            val defaultPlayerUiController =
                DefaultPlayerUiController(playerView, youTubePlayer)
            defaultPlayerUiController.showFullscreenButton(true)

            defaultPlayerUiController.setFullscreenButtonClickListener {
                fullScreenCallback()
            }


            playerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

            when (videoState.value) {
                "PAUSED" -> {
                    youTubePlayer.cueVideo(videoId, videoSeconds.value)
                }
                "PLAYING", "BUFFERING" -> {
                    youTubePlayer.loadVideo(videoId, videoSeconds.value)
                }
                else -> {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState
        ) {
            super.onStateChange(youTubePlayer, state)
            videoState.value = state.toString()
        }

        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
            super.onCurrentSecond(youTubePlayer, second)
            videoSeconds.value = second
        }

    }
    return listener
}

fun getYoutubeVideoId(url: String): String? {
    val parts = url.split("=")
    if (parts[0] != "https://www.youtube.com/watch?v") return null
    return parts.last()
}