package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
    ) {
        val modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
        StatusInfo(
            modifier = modifier,
            launch = launch
        )
        RocketInfo(
            modifier = modifier,
            rocket = launch.rocket,
            imageUri = launch.image
        )
        MissionInfo(
            modifier = modifier,
            mission = launch.mission
        )
        LocationInfo(
            modifier = modifier,
            pad = launch.pad
        )
    }
}