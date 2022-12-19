package com.thewire.wenlaunch.presentation.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Webcast(
    modifier: Modifier = Modifier,
    uri: String?,
    videoSeconds: MutableState<Float>,
    videoState: MutableState<String>,
    onFullScreen: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
            ) {
                Text(
                    "Webcast",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                videoUri = uri,
                videoSeconds,
                videoState,
                false,
                onFullScreen
            )
        }
    }
}