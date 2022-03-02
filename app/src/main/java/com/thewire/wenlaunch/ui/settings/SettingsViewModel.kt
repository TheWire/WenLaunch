package com.thewire.wenlaunch.ui.settings

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.repository.store.SettingsStore
import javax.inject.Inject

class SettingsViewModel
@Inject
constructor(
    settingsStore: SettingsStore
) : ViewModel() {

    val notificationsOn = mutableStateOf(false)
    val notifications = mutableStateMapOf<NotificationLevel, Boolean>()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.DarkMode -> TODO()
            SettingsEvent.NotificationsAllOff -> TODO()
            SettingsEvent.NotificationsAllOn -> TODO()
            is SettingsEvent.NotificationsChange -> TODO()
        }
    }
}