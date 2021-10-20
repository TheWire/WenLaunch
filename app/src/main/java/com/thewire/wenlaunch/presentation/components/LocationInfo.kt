package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.Mission
import com.thewire.wenlaunch.domain.model.Pad

@Composable
fun LocationInfo(
    pad: Pad
) {
    Card(
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Launch Location",
                style = MaterialTheme.typography.h5
            )
            Text(
                pad.name ?: "Unknown Pad",
                style = MaterialTheme.typography.h6
            )
            Text(
                "Location",
                style = MaterialTheme.typography.h6
            )
            Text(
                pad.location.name ?: "Unknown Location",
            )
            LaunchImage(
                imageUri = pad.location.map_image,
            )
        }
    }
}