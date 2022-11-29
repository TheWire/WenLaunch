package com.thewire.wenlaunch.presentation.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

private const val TAG = "VIDEO_PLAYER"

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String
) {
    val videoId = getYoutubeVideoId(videoUri)
    if(videoId.isNullOrEmpty()) {
        modifier.background(color = Color.Black)
        Box(
            modifier = modifier
        )

    } else {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                val player = YouTubePlayerView(context)
                player.enableAutomaticInitialization = false
                val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
                player.initialize(getPlayer(player, videoId), options)
                player
            }
        )
    }

}

fun getPlayer(playerView: YouTubePlayerView, videoId: String): YouTubePlayerListener {

    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            val defaultPlayerUiController =
                DefaultPlayerUiController(playerView, youTubePlayer)
            defaultPlayerUiController.showFullscreenButton(true)

            defaultPlayerUiController.setFullScreenButtonClickListener {
                if (playerView.isFullScreen()) {
                    playerView.exitFullScreen()

                } else {
                    playerView.enterFullScreen()
                }
            }


            playerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

            youTubePlayer.cueVideo(videoId = videoId, 0f)
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