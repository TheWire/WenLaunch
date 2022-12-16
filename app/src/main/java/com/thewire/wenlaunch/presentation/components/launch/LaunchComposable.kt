package com.thewire.wenlaunch.presentation.components.launch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.presentation.components.LocationInfo
import com.thewire.wenlaunch.presentation.components.MissionInfo
import com.thewire.wenlaunch.presentation.components.RocketInfo
import com.thewire.wenlaunch.presentation.components.StatusInfo
import com.thewire.wenlaunch.presentation.components.media.Webcast
import com.thewire.wenlaunch.ui.launch.LaunchViewModel

@Composable
fun LaunchComposable(
    modifier: Modifier = Modifier,
    viewModel: LaunchViewModel,
    launch: Launch,
    onFullScreenVideo: () -> Unit
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
                onFullScreen = onFullScreenVideo
            )
        }
        Webcast(
            modifier = infoModifier.fillMaxWidth(),
            uri = "https://www.youtube.com/watch?v=VsacL7_yDSo",
            viewModel.videoSeconds,
            viewModel.videoState,
            onFullScreen = onFullScreenVideo
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