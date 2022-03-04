package com.thewire.wenlaunch.ui.settings

import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

sealed class SettingsEvent {
    data class NotificationsChange(val level: NotificationLevel, val state: Boolean): SettingsEvent()
    data class NotificationsToggle(val state: Boolean): SettingsEvent()
    object DarkMode: SettingsEvent()
}
