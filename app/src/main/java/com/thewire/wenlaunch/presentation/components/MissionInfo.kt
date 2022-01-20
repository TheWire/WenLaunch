package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Mission

@Composable
fun MissionInfo(
    modifier: Modifier = Modifier,
    mission: Mission
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Text(
                "Mission",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                mission.name ?: "Unknown Mission",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                mission.orbit.name ?: "Unknown Orbit",
                modifier = Modifier.padding(6.dp),
            )
            Text(
                mission.description ?: "No Description",
                modifier = Modifier.padding(6.dp),
            )
        }
    }
}