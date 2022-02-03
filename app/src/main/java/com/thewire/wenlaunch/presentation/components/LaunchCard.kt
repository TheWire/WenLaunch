package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.util.loadPicture
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val DEFAULT_LAUNCH_IMAGE = R.drawable.default_launch

@Composable
fun LaunchCard(
    modifier: Modifier = Modifier,
    launch: Launch,
    onClick: () -> Unit,
) {
    Card(
    shape = MaterialTheme.shapes.small,
    modifier = modifier
    .padding(
    bottom = 7.dp,
    top = 7.dp
    )
    .clickable(onClick = onClick),
    elevation = 4.dp
    ) {
        Column () {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(4.dp)
            ) {
                Text(
                    text = launch.mission.name ?: "Unknown Mission",
                    style = MaterialTheme.typography.h5,
                )
                LaunchStatusIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    launchStatus = launch.status?.abbrev,
                    padding = 3.dp,
                    fontSize = 11.sp,
                )

            }
            Row() {
                LaunchListInfo(
                    modifier = Modifier
                        .weight(6f)
                        .padding(4.dp),
                    launch = launch,
                )
                val image = loadPicture(
                    uri = launch.image, defaultImage = DEFAULT_LAUNCH_IMAGE
                ).value
                image?.let { img ->
                    Image(
                        modifier = Modifier
                            .height(200.dp)
                            .weight(4f)
                            .align(Alignment.CenterVertically),
                        bitmap = img.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = launch.name ?: "launch pic",
                    )
                }
            }
        }

    }
}

@Composable
fun LaunchListInfo(
    modifier: Modifier = Modifier,
    launch: Launch,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            launch.rocket.configuration.fullName ?: "Unknown Rocket",
            style = MaterialTheme.typography.h6
        )
        Text(
            launch.pad.location.name ?: "Unknown Location",
        )
        val timeString: String = when(launch.status?.abbrev) {
            LaunchStatus.GO -> getTimeString(launch.net, "H:mm EE d MMM yyyy")
            LaunchStatus.TBC -> "NET " + getTimeString(launch.net,"H:mm EE d MMM yyyy")
            LaunchStatus.TBD -> "NET " + getTimeString(launch.net,"d MMM yyyy")
            else -> getTimeString(launch.net,"")
        }
        Text(
            timeString
        )
        if(launch.webcastLive) {
            Spacer(modifier = Modifier.height(10.dp))
            WebcastLiveIndicator(
                modifier = Modifier
                    .padding(4.dp, 2.dp),
                text = "LIVE",
                uri = null
            )
        }
    }
}

fun getTimeString(time: ZonedDateTime?, pattern: String): String {
    if(time == null) {
        return "Unknown Time"
    }
    return if(pattern.isNotEmpty()) {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        time.withZoneSameInstant(
            ZoneId.systemDefault()
        )?.format(formatter).toString()
    } else {
        "Unknown Time"
    }
}