package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Mission

@Composable
fun MissionInfo(
    mission: Mission
) {
    Card(
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Mission",
                style = MaterialTheme.typography.h5
            )
            Text(
                mission.name ?: "Unknown Mission",
                style = MaterialTheme.typography.h6
            )
            Text(
                "Orbit",
                style = MaterialTheme.typography.h6
            )
            Text(
                mission.orbit.name ?: "Unknown Orbit",
            )
            Text(
                mission.description ?: "No Description",
            )
        }
    }
}