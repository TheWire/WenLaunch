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
            Text(
                text = launch.mission.name ?: "unknown mission",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(4.dp),
                style = MaterialTheme.typography.h5,
            )
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
        modifier = modifier
    ) {
        Text(
            launch.rocket.configuration.fullName ?: "Unknown Rocket",
            style = MaterialTheme.typography.h6
        )
        Text(
            launch.pad.location.name ?: "Unknown Location",
        )
        val timeString: String = when(launch.status?.abbrev) {
            LaunchStatus.GO -> getTimeString(launch.net, "H:mm:ss EE D MM yyyy")
            LaunchStatus.TBC -> "NET " + getTimeString(launch.net,"H:mm EE D MM yyyy")
            LaunchStatus.TBD -> "NET " + getTimeString(launch.net,"EE D MM yyyy")
            else -> getTimeString(launch.net,"")
        }
        Text(
            timeString
        )
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