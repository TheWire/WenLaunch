package com.thewire.wenlaunch.presentation.components


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

private const val TAG = "VIDEO_PLAYER"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String,
    videoSeconds: MutableState<Float>,
    videoState: MutableState<String>,
    fullscreen: MutableState<Boolean>,
) {
    val videoId = getYoutubeVideoId(videoUri)
    val activity = LocalContext.current.findActivity()
    if(videoId.isNullOrEmpty() || activity == null) {
        modifier.background(color = Color.Black)
        Box(
            modifier = modifier
        )

    } else {
        val lifecycle = LocalLifecycleOwner.current.lifecycle


        if(fullscreen.value) {
            Dialog(
                onDismissRequest = {
                    fullscreen.value = false
                },
                properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = true, usePlatformDefaultWidth = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    val configuration = LocalConfiguration.current
                    val width = (configuration.screenHeightDp / 9) * 16
                    AndroidView(
                        modifier = Modifier
                            .width(width.dp)
                            .height(configuration.screenHeightDp.dp)
                            .align(Alignment.Center),
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
                                    fullscreen,
                                    activity,
                                ), options
                            )
                            player
                        }
                    )
                }
            }
        } else {
            AndroidView(
                modifier = modifier,
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
                            fullscreen,
                            activity,
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
    fullscreen: MutableState<Boolean>,
    activity: Activity
): YouTubePlayerListener {

    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {

            val defaultPlayerUiController =
                DefaultPlayerUiController(playerView, youTubePlayer)
            defaultPlayerUiController.showFullscreenButton(true)

            defaultPlayerUiController.setFullScreenButtonClickListener {
                fullscreen.value = !fullscreen.value
                if(fullscreen.value) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
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
            Log.i("YOUTUBE", state.toString())
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
    parts.forEach {
        Log.i(TAG, it)
    }


    if(parts[0] != "https://www.youtube.com/watch?v") return null
    return parts.last()
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}