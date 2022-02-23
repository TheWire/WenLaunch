package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.thewire.wenlaunch.R


const val DEFAULT_LAUNCH_IMAGE = R.drawable.default_launch
const val DEFAULT_LOCATION_IMAGE = R.drawable.default_location

@Composable
fun LaunchImage(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    defaultImage: Int = DEFAULT_LAUNCH_IMAGE,
    contentDescription: String = ""
) {
    Image(
        modifier = modifier,
        painter = rememberImagePainter(
            data = imageUri ?: "error",
            builder = {
                placeholder(defaultImage)
                error(defaultImage)
            }
        ),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription,
    )
}