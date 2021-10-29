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

                Text(
                    text = getStatusText(launch.status?.abbrev),
                    modifier = Modifier
                        .background(color = getStatusColor(launch.status?.abbrev), shape = CircleShape)
                        .padding(6.dp)
                        .align(Alignment.CenterEnd),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                launch.net?.toString() ?: "Unknown Time",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )

        }
    }
}

fun getStatusColor(launchStatus: LaunchStatus?): Color {
    return when(launchStatus) {
        GO -> Color.Green
        TBD -> Color.Yellow
        TBC -> Color(0xffa9fca7)
        OTHER -> Color(0xffeb7434)
        else -> Color(0xffeb7434)
    }
}

fun getStatusText(launchStatus: LaunchStatus?): String {
    return when(launchStatus) {
        GO -> GO.toString()
        TBD -> TBD.toString()
        TBC -> TBC.toString()
        OTHER -> "?"
        else -> "?"
    }
}