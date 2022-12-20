package com.thewire.wenlaunch.presentation.components.Info

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Rocket
import com.thewire.wenlaunch.presentation.components.DEFAULT_LAUNCH_IMAGE
import com.thewire.wenlaunch.presentation.components.InfoCard
import com.thewire.wenlaunch.presentation.components.LaunchImage

@Composable
fun RocketInfo(
    modifier: Modifier = Modifier,
    rocket: Rocket,
    imageUri: String?
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
//                .height(450.dp),
            imageUri = imageUri,
            defaultImage = DEFAULT_LAUNCH_IMAGE
        )
    }
}
