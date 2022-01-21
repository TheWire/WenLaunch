package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.util.loadPicture

const val DEFAULT_LAUNCH_IMAGE = R.drawable.default_launch

@Composable
fun LaunchCard(
    modifier: Modifier = Modifier,
    launch: Launch,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(
                bottom = 7.dp,
                top = 7.dp
            )
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column() {
                val image = loadPicture(
                    uri = launch.image, defaultImage = DEFAULT_LAUNCH_IMAGE
                ).value
                image?.let { img ->
                    Image(
                        modifier = Modifier
                            .height(380.dp)
                            .align(Alignment.CenterHorizontally),
                        bitmap = img.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = launch.name ?: "launch pic",
                    )
                }
            Text(
                text = launch.name ?: "unknown launch",
                modifier = Modifier
                    .padding(4.dp),
                style = MaterialTheme.typography.h5,
            )
        }

    }
}