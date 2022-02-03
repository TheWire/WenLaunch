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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.ui.launch.DateTimePeriod
import com.thewire.wenlaunch.ui.launch.LaunchCountdown
import com.thewire.wenlaunch.ui.launch.TimePeriod
import com.thewire.wenlaunch.util.loadPicture

@Composable
fun LaunchView(
    modifier: Modifier = Modifier,
    launch: Launch,
    countdown: State<DateTimePeriod?>?,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
    ) {
        val infoModifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
        StatusInfo(
            modifier = infoModifier.fillMaxWidth(),
            launch = launch,
            countdown = countdown,
        )
        RocketInfo(
            modifier = infoModifier.fillMaxWidth(),
            rocket = launch.rocket,
            imageUri = launch.image
        )
        MissionInfo(
            modifier = infoModifier.fillMaxWidth(),
            mission = launch.mission
        )
        LocationInfo(
            modifier = infoModifier.fillMaxWidth(),
            pad = launch.pad
        )
    }
}