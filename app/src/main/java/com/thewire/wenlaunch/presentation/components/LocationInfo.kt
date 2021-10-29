package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Pad

@Composable
fun LocationInfo(
    modifier: Modifier = Modifier,
    pad: Pad
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Location",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                pad.name ?: "Unknown Pad",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                pad.location.name ?: "Unknown Location",
                modifier = Modifier.padding(6.dp),
            )
            LaunchImage(
                modifier = Modifier.height(300.dp),
                imageUri = pad.location.map_image,
            )
        }
    }
}