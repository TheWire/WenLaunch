package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus.*
import com.thewire.wenlaunch.ui.launch.DateTimePeriod
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StatusInfo(
    modifier: Modifier = Modifier,
    launch: Launch,
    countdown: DateTimePeriod?,
) {
    InfoCard(
        modifier = modifier,
        headingText = "Status",
        headingExtraContent = {
            if (launch.status?.abbrev != SUCCESS) {
                LaunchStatusIndicator(
                    launchStatus = launch.status?.abbrev
                )
            }
        }
    ) {
        if (launch.status?.abbrev == TBC ||
            launch.status?.abbrev == TBD ||
            launch.status?.abbrev == OTHER
        ) {

            Text(
                "No Earlier Than",
                style = MaterialTheme.typography.subtitle2
            )
        }
        val formatter = DateTimeFormatter.ofPattern("H:mm:ss EEEE d MMMM yyyy z")
        val time = launch.net.withZoneSameInstant(
            ZoneId.systemDefault()
        )?.format(formatter).toString()
        Text(
            text = time,
            style = MaterialTheme.typography.h6
        )
        countdown?.let { countdown ->
            Spacer(modifier = Modifier.padding(4.dp))
            Countdown(countdown = countdown)
        }
//                if(launch.vidUris.isNotEmpty()) {
//                    Text(launch.vidUris[0].uri.toString())
//                }
    }
}


@Composable
fun Countdown(modifier: Modifier = Modifier, countdown: DateTimePeriod) {
    val timePeriod = countdown.timePeriod
    val period = countdown.period
    Text("T minus")
    Column(modifier = modifier) {
        val yearString = if (period.years > 0) "${period.years} Years " else ""
        val monthString =
            if (period.months > 0 || period.years > 0) "${period.months} Months " else ""
        val dayString =
            if (period.days > 0 || period.months > 0 || period.years > 0) "${period.days} Days" else ""
        val periodString = "$yearString$monthString$dayString"
        if (periodString.isNotEmpty()) {
            Text(
                text = periodString,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
        val hourString = if (timePeriod.hours > 0) "${timePeriod.hours} Hours " else ""
        val minuteString =
            if (timePeriod.minutes > 0 || timePeriod.hours > 0) "${timePeriod.minutes} Mins " else ""
        val secondString = "${timePeriod.seconds} Secs"
        val timeString = "$hourString$minuteString$secondString"
        Text(
            text = timeString,
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.h6
        )
    }

}