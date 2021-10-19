package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.util.loadPicture

@Composable
fun LaunchView(
    launch: Launch
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(launch.net?: "error")
        launch.image?.let { uri ->
            val image = loadPicture(
                uri = uri, defaultImage = DEFAULT_LAUNCH_IMAGE
            ).value
            image?.let { img ->
                Image(
                    modifier = Modifier
                        .height(450.dp),
                    bitmap = img.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = launch.name ?: "launch pic",
                )
            }
        }
        Text(
            "Launch Site",
            style = MaterialTheme.typography.h5
        )
        Text(
            launch.pad.name ?: "unknown"
        )
        Text(
            launch.pad.location.name ?: "unknown"
        )

        Text(
            "Orbit",
            style = MaterialTheme.typography.h5
        )
        Text(
            launch.mission.orbit.name ?: "unknown"
        )

        Text(
            "Mission",
            style = MaterialTheme.typography.h5
        )
        Text(
            launch.mission.name ?: "unknown"
        )
        Text(
            launch.mission.description ?: "unknown"
        )
    }
}