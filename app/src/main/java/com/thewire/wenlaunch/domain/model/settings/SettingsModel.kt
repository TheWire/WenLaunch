package com.thewire.wenlaunch.domain.model.settings

data class SettingsModel (
    var darkMode: Boolean = true,
    var notifications: Map<NotificationLevel, Boolean> = mapOf()
)

enum class NotificationLevel(val time: Long) {
    HOURS24(1440),
    HOURS1(60),
    WEBCAST(240),
    MINUTES10(10),
    MINUTES1(1),
    LAUNCH(0),
}