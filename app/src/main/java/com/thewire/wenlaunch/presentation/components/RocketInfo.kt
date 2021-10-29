package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Rocket
import com.thewire.wenlaunch.util.loadPicture

@Composable
fun RocketInfo(
    modifier: Modifier = Modifier,
    rocket: Rocket,
    imageUri: Uri?
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Rocket",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                rocket.configuration.name ?: "Unknown Rocket",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )
            LaunchImage(
                modifier = Modifier.align(Alignment.CenterHorizontally).height(450.dp),
                imageUri = imageUri,
            )
        }
    }
}
