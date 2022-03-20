package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

enum class MockNotificationLevel(time: Long, description: String) : NotificationLevel(time, description){
    DEFAULT(1, "Unknown"),
    HOURS24(1, "24 Hours"),
    HOURS1(1, "1 Hour"),
    WEBCAST(1, "Webcast Live"),
    MINUTES10(1, "10 minutes"),
    MINUTES1(1, "1 minute"),
    LAUNCH(0, "launch imminent"),
}