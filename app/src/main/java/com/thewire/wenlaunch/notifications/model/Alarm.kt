package com.thewire.wenlaunch.notifications.model

data class Alarm(
    val requestId: Int,
    val time: Long,
    val launchId: String
)
