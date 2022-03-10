package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.ui.launch.DateTimePeriod

@Composable
fun LaunchView(
    modifier: Modifier = Modifier,
    launch: Launch,
    countdown: DateTimePeriod?,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
    ) {
        val infoModifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
        if (launch.webcastLive && launch.vidUris.isNotEmpty()) {
            Webcast(
                modifier = infoModifier.fillMaxWidth(),
                uri = launch.vidUris[0].uri ?: Uri.EMPTY
            )
        }
        StatusInfo(
            modifier = infoModifier.fillMaxWidth(),
            launch = launch,
            countdown = countdown,
        )
        launch.rocket?.let { rocket ->
            RocketInfo(
                modifier = infoModifier.fillMaxWidth(),
                rocket = rocket,
                imageUri = launch.image
            )
        }
        launch.mission?.let { mission ->
            MissionInfo(
                modifier = infoModifier.fillMaxWidth(),
                mission = mission
            )
        }
        launch.pad?.let { pad ->
            LocationInfo(
                modifier = infoModifier.fillMaxWidth(),
                pad = pad
            )
        }
    }
}