package com.thewire.wenlaunch.domain.model.settings

data class SettingsModel (
    var darkMode: Boolean = true,
    var notifications: Map<NotificationLevel, Boolean> = mapOf()
)

enum class NotificationLevel {
    HOURS24,
    HOURS1,
    WEBCAST,
    MINUTES10,
    MINUTES1,
    LAUNCH,
}