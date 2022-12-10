package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.ui.launch.LaunchViewModel

@Composable
fun LaunchView(
    modifier: Modifier = Modifier,
    viewModel: LaunchViewModel,
    launch: Launch,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
    ) {
        val infoModifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
        if (launch.webcastLive && launch.vidUrls.isNotEmpty()) {
            Webcast(
                modifier = infoModifier.fillMaxWidth(),
                uri = launch.vidUrls[0].uri,
                viewModel.videoSeconds,
                viewModel.videoState,
                viewModel.fullscreen
            )
        }
        Webcast(
            modifier = infoModifier.fillMaxWidth(),
            uri = "https://www.youtube.com/watch?v=fXYyE7UI9SI",
            viewModel.videoSeconds,
            viewModel.videoState,
            viewModel.fullscreen
        )
        StatusInfo(
            modifier = infoModifier.fillMaxWidth(),
            launch = launch,
            countdown = viewModel.countdownState.value,
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