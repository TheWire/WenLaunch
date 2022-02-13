package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.LaunchStatus.*
import com.thewire.wenlaunch.ui.launch.DateTimePeriod
import com.thewire.wenlaunch.ui.launch.TimePeriod
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StatusInfo(
    modifier: Modifier = Modifier,
    launch: Launch,
    countdown: DateTimePeriod?,
) {
    Card(
        modifier = modifier,
        elevation = 6.dp
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primary)
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Status",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onPrimary
                )
                if(launch.status?.abbrev != SUCCESS) {
                    LaunchStatusIndicator(
                        launchStatus = launch.status?.abbrev
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(6.dp)
            ) {
                if(launch.status?.abbrev == TBC ||
                    launch.status?.abbrev == TBD ||
                    launch.status?.abbrev == OTHER) {

                    Text(
                        "No Earlier Than",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                val formatter = DateTimeFormatter.ofPattern("H:mm:ss EEEE d MMMM yyyy z")
                val time = launch.net?.withZoneSameInstant(
                    ZoneId.systemDefault())?.format(formatter).toString()
                launch.net
                Text(text = time,
                    style = MaterialTheme.typography.h6
                )
                countdown?.let { countdown ->
                    Spacer(modifier = Modifier.padding(4.dp))
                    Countdown(countdown = countdown)
                }
            }
        }
    }
}

@Composable
fun Countdown(modifier: Modifier = Modifier, countdown: DateTimePeriod) {
    val timePeriod = countdown.timePeriod
    val period = countdown.period
    Text("T minus")
    Column(modifier = modifier) {
        val yearString = if(period.years > 0) "${period.years} Years " else ""
        val monthString = if(period.months > 0 || period.years > 0) "${period.months} Months " else ""
        val dayString = if(period.days > 0 || period.months > 0 || period.years > 0) "${period.days} Days" else ""
        val periodString = "$yearString$monthString$dayString"
        if(periodString.isNotEmpty()) {
            Text(
                text = periodString,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
        val hourString = if(timePeriod.hours > 0) "${timePeriod.hours} Hours " else ""
        val minuteString = if(timePeriod.minutes > 0 || timePeriod.hours > 0) "${timePeriod.minutes } Mins " else ""
        val secondString = "${timePeriod.seconds} Secs"
        val timeString = "$hourString$minuteString$secondString"
        Text(
            text = timeString,
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.h6
        )
    }

}