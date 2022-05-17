package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Mission
import com.thewire.wenlaunch.util.ifEmptyNull

@Composable
fun MissionInfo(
    modifier: Modifier = Modifier,
    mission: Mission
) {
    InfoCard(
        modifier = modifier,
        headingText = "Mission",
    ) {
        Text(
            mission.name.ifEmpty { "Unknown Mission" },
            modifier = Modifier.padding(vertical = 6.dp),
            style = MaterialTheme.typography.h6
        )
        Text(
            mission.orbit?.name?.ifEmptyNull() ?: "Unknown Orbit",
            modifier = Modifier.padding(vertical = 6.dp),
        )
        Text(
            mission.description.ifEmpty { "No Description" },
            modifier = Modifier.padding(vertical = 6.dp),
        )
    }
}