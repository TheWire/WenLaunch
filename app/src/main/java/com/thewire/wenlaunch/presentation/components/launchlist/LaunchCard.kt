package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.presentation.components.layout.LaunchCardLayout
import com.thewire.wenlaunch.util.ifEmptyNull
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LaunchCard(
    modifier: Modifier = Modifier,
    launch: Launch,
    onClick: (Boolean) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(6.dp)
            .clickable(onClick = { onClick(false) }),
        elevation = 4.dp
    ) {

        LaunchCardLayout(
            modifier = modifier,
            image = launchPainter(launch.image),
            imageDescription = "launch image",
            header = {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = launch.mission?.name?.ifEmptyNull() ?: "Unknown Mission",
                        modifier = Modifier
                            .weight(1f),
                        style = MaterialTheme.typography.h5,
                    )
                    LaunchStatusIndicator(
                        launchStatus = launch.status?.abbrev,
                        padding = 3.dp,
                        fontSize = 11.sp,
                    )

                }
            }
        ) {
            LaunchListInfo(
                modifier = Modifier
                    .width(200.dp)
                    .padding(4.dp),
                launch = launch,
                onLiveClick = { onClick(true)}
            )
        }
    }
}

@Composable
fun LaunchListInfo(
    modifier: Modifier = Modifier,
    launch: Launch,
    onLiveClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = launch.rocket?.configuration?.fullName?.ifEmptyNull() ?: "Unknown Rocket",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = launch.pad?.location?.name?.ifEmptyNull() ?: "Unknown Location",
        )
        val timeString: String = when (launch.status?.abbrev) {
            LaunchStatus.TBC -> "NET " + getTimeString(launch.net, "H:mm EE d MMM yyyy")
            LaunchStatus.TBD -> "NET " + getTimeString(launch.net, "d MMM yyyy")
            else -> getTimeString(launch.net, "H:mm EE d MMM yyyy")
        }
        Text(
            timeString
        )
        if (launch.webcastLive) {
            Spacer(modifier = Modifier.height(10.dp))
            WebcastLiveIndicator(
                text = "LIVE",
                onClick = onLiveClick,
                contentPadding = PaddingValues(6.dp, 3.dp, 15.dp, 3.dp)
            )
        }
    }
}

fun getTimeString(time: ZonedDateTime, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return time.withZoneSameInstant(
            ZoneId.systemDefault()
        )?.format(formatter).toString()
}