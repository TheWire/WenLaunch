package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
    pad: Pad
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
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
                "Location",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                pad.location.name ?: "Unknown Location",
                modifier = Modifier.padding(6.dp),
            )
            LaunchImage(
                imageUri = pad.location.map_image,
            )
        }
    }
}