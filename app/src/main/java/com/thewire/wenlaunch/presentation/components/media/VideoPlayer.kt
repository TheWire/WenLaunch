package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.thewire.wenlaunch.R

const val TAG = "VIDEO_PLAYER"

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String
) {
    val context = LocalContext.current
    val player = remember(context) { ExoPlayer.Builder(context).build() }
    player.addListener(object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            error.message?.let {
                Log.e(TAG, it)
            }
        }
    })
    val mediaItem = MediaItem.Builder()
        .setUri(videoUri)
        .build()
    player.setMediaItem(mediaItem)
    LaunchedEffect(player) {
        player.prepare()
    }
    AndroidView(
        factory = {
            val layout = LayoutInflater.from(it).inflate(R.layout.video_player, null, false)
            layout.findViewById(R.id.player) as PlayerView
        },
        modifier = modifier,
        update = {
            it.player = player
        }
    )
}