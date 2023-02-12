package com.thewire.wenlaunch.ui.settings

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.presentation.BaseApplication
import javax.inject.Inject

class SettingsViewModel
@Inject
constructor(
    private val application: BaseApplication,
) : ViewModel() {

    val darkMode = application.darkMode
    var notifications: MutableState<Map<NotificationLevel, Boolean>> = application.notifications

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.DarkMode -> {
                application.toggleDarkTheme()
            }
            is SettingsEvent.NotificationsToggle -> {
                val newMap = notifications.value.mapValues {
                    event.state
                }
                application.setNotifications(newMap, event.activity)
            }
            is SettingsEvent.NotificationsChange -> {
                val newMap = notifications.value.toMutableMap()
                newMap[event.level] = event.state
                application.setNotifications(newMap, event.activity)
            }
        }
    }
}