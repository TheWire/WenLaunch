package com.thewire.wenlaunch.ui.settings

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.repository.store.SettingsStore
import javax.inject.Inject

class SettingsViewModel
@Inject
constructor(
    private val application: BaseApplication,
) : ViewModel() {

    val darkMode = application.darkMode
    val notificationsOn = mutableStateOf(false)
    val notifications = application.notifications

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.DarkMode -> {
                application.toggleDarkTheme()
            }
            SettingsEvent.NotificationsAllOff -> TODO()
            SettingsEvent.NotificationsAllOn -> TODO()
            is SettingsEvent.NotificationsChange -> TODO()
        }
    }
}