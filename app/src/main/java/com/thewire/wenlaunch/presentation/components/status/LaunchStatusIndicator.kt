package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
            text = launchStatus?.status ?: "?",
            modifier = modifier
                .padding(padding),
            color = Color.Black,
            fontWeight = fontWeight,
            fontSize = fontSize
        )
    }

}

fun getStatusColor(launchStatus: LaunchStatus?): Color {
    return when (launchStatus) {
        LaunchStatus.GO -> Color.Green
        LaunchStatus.HOLD -> Color.Red
        LaunchStatus.TBD -> Color.Yellow
        LaunchStatus.TBC -> Color(0xffa9fca7)
        LaunchStatus.IN_FLIGHT -> Color(0xff4287f5)
        LaunchStatus.PARTIAL_FAILURE -> Color(0xFF663C3C)
        LaunchStatus.FAILURE -> Color(0xff121212)
        LaunchStatus.OTHER -> Color(0xffeb7434)
        else -> Color(0xffeb7434)
    }
}