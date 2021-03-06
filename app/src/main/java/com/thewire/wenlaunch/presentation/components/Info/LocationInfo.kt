package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Pad
import com.thewire.wenlaunch.util.ifEmptyNull

@Composable
fun LocationInfo(
    modifier: Modifier = Modifier,
    pad: Pad
) {
    InfoCard(
        modifier = modifier,
        headingText = "Location",
        bodyPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = pad.name.ifEmpty { "Unknown Pad" },
            modifier = Modifier.padding(6.dp),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = pad.location?.name?.ifEmptyNull() ?: "Unknown Location",
            modifier = Modifier.padding(6.dp),
        )
        LaunchImage(
            modifier = Modifier.height(300.dp),
            imageUri = pad.location?.mapImage,
            defaultImage = DEFAULT_LOCATION_IMAGE
        )
    }
}