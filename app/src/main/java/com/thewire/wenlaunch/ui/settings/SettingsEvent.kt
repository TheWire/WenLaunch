package com.thewire.wenlaunch.ui.settings

import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

sealed class SettingsEvent {
    data class NotificationsChange(val level: NotificationLevel): SettingsEvent()
    object NotificationsAllOn: SettingsEvent()
    object NotificationsAllOff: SettingsEvent()
    object DarkMode: SettingsEvent()
}
