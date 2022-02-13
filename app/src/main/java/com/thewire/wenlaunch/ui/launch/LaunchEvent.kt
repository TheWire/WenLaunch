package com.thewire.wenlaunch.ui.launch

import java.time.ZonedDateTime

sealed class LaunchEvent {
    object Start: LaunchEvent()
    object Stop: LaunchEvent()
    data class GetLaunch(
        val id: String
    ): LaunchEvent()
    data class StartCountdown(
        val launchTime: ZonedDateTime
    ): LaunchEvent()
}
