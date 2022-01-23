package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.LaunchStatus.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StatusInfo(
    modifier: Modifier = Modifier,
    launch: Launch
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
            ) {
                Text(
                    "Status",
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
                LaunchStatusIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    launchStatus = launch.status?.abbrev
                )
            }
            if(launch.status?.abbrev == TBC ||
                launch.status?.abbrev == TBD ||
                launch.status?.abbrev == OTHER) {

                Text(
                    "No Earlier Than",
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.subtitle2
                )
            }
            val formatter = DateTimeFormatter.ofPattern("H:mm:ss EEEE d MMMM yyyy z")
            val time = launch.net?.withZoneSameInstant(
                ZoneId.systemDefault())?.format(formatter).toString()
            launch.net
            Text(text = time,
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )

        }
    }
}