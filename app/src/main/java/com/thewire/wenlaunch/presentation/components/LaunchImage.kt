package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.util.loadPicture


@Composable
fun LaunchImage(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    height: Dp = 200.dp,
    contentDescription: String = ""
) {
    imageUri?.let { uri ->
        val image = loadPicture(
            uri = uri, defaultImage = DEFAULT_LAUNCH_IMAGE
        ).value
        image?.let { img ->
            Image(
                modifier = modifier
                    .height(height),
                bitmap = img.asImageBitmap(),
                contentScale = ContentScale.Crop,
                contentDescription = contentDescription,
            )
        }
    }
}