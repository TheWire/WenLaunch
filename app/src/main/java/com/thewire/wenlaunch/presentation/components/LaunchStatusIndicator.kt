package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewire.wenlaunch.domain.model.LaunchStatus

@Composable
fun LaunchStatusIndicator(
    modifier: Modifier = Modifier,
    launchStatus: LaunchStatus?,
    padding: Dp = 6.dp,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 16.sp,
) {
    Surface(
        modifier = modifier,
        color = getStatusColor(launchStatus),
        shape = CircleShape
    ) {
        Text(
            text = getStatusText(launchStatus),
            modifier = modifier
                .padding(padding),
            color = Color.Black,
            fontWeight = fontWeight,
            fontSize = fontSize
        )
    }

}

fun getStatusColor(launchStatus: LaunchStatus?): Color {
    return when(launchStatus) {
        LaunchStatus.GO -> Color.Green
        LaunchStatus.TBD -> Color.Yellow
        LaunchStatus.TBC -> Color(0xffa9fca7)
        LaunchStatus.OTHER -> Color(0xffeb7434)
        else -> Color(0xffeb7434)
    }
}

fun getStatusText(launchStatus: LaunchStatus?): String {
    return when(launchStatus) {
        LaunchStatus.GO -> LaunchStatus.GO.toString()
        LaunchStatus.TBD -> LaunchStatus.TBD.toString()
        LaunchStatus.TBC -> LaunchStatus.TBC.toString()
        LaunchStatus.OTHER -> "?"
        else -> "?"
    }
}