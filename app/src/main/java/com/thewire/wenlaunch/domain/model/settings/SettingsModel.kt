package com.thewire.wenlaunch.domain.model.settings

data class SettingsModel (
    var darkMode: Boolean = true,
    var notifications: Map<NotificationLevel, Boolean> = mapOf()
)

enum class NotificationLevel(val time: Long, val description: String) {
    DEFAULT(0, "Unknown"),
    HOURS24(1440, "24 Hours"),
    HOURS1(60, "1 Hour"),
//    WEBCAST(240, "Webcast Live"),
    MINUTES10(10, "10 minutes"),
    MINUTES1(1, "1 minute"),
//    LAUNCH(0, "launch imminent"),
}