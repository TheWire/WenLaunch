package com.thewire.wenlaunch.ui.settings

import android.app.Activity
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

sealed class SettingsEvent {
    data class NotificationsChange(val level: NotificationLevel, val state: Boolean, val activity: Activity?): SettingsEvent()
    data class NotificationsToggle(val state: Boolean, val activity: Activity?): SettingsEvent()
    object DarkMode: SettingsEvent()
}
