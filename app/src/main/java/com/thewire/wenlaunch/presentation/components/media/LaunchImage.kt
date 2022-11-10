package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.thewire.wenlaunch.R


const val DEFAULT_LAUNCH_IMAGE = R.drawable.default_launch
const val DEFAULT_LOCATION_IMAGE = R.drawable.default_location

@Composable
fun LaunchImage(
    modifier: Modifier = Modifier,
    imageUri: String?,
    defaultImage: Int = DEFAULT_LAUNCH_IMAGE,
    contentDescription: String = ""
) {
    Image(
        modifier = modifier,
        painter = launchPainter(imageUri, defaultImage),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription,
    )
}

@Composable
fun launchPainter(
    imageUri: String?,
    defaultImage: Int = DEFAULT_LAUNCH_IMAGE,
) : Painter {
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUri ?: "error")
            .placeholder(defaultImage)
            .build()
    )
}