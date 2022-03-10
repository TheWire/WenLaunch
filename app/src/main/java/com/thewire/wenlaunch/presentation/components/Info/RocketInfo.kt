package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Rocket

@Composable
fun RocketInfo(
    modifier: Modifier = Modifier,
    rocket: Rocket,
    imageUri: Uri?
) {
    InfoCard(
        modifier = modifier,
        headingText = "Rocket",
        bodyPadding = PaddingValues(0.dp)
    ) {
        Text(
            rocket.configuration?.name ?: "Unknown Rocket",
            modifier = Modifier.padding(6.dp),
            style = MaterialTheme.typography.h6
        )
        LaunchImage(
            modifier = Modifier.align(Alignment.CenterHorizontally).height(450.dp),
            imageUri = imageUri,
            defaultImage = DEFAULT_LAUNCH_IMAGE
        )
    }
}
