package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Rocket
import com.thewire.wenlaunch.util.loadPicture

@Composable
fun RocketInfo(
    rocket: Rocket,
    imageUri: Uri?
) {
    Card(
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Rocket",
                style = MaterialTheme.typography.h5
            )
            Text(
                rocket.configuration.name ?: "Unknown Rocket",
                style = MaterialTheme.typography.h6
            )
            LaunchImage(
                imageUri = imageUri,
                height = 450.dp
            )
        }
    }
}
