package com.thewire.wenlaunch.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.repository.store.SettingsStore
import com.thewire.wenlaunch.repository.store.SettingsStoreResult
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var settingsStore: SettingsStore
    val settingsModel: MutableState<SettingsModel> = mutableStateOf(SettingsModel())
    val notifications: MutableState<Map<NotificationLevel, Boolean>> = mutableStateOf(HashMap())
    val darkMode = mutableStateOf(false)
    override fun onCreate() {
        super.onCreate()
        loadSettings()
    }

    private fun loadSettings() {
        CoroutineScope(Dispatchers.Main).launch {
            when (val res = settingsStore.getSettings()) {
                is SettingsStoreResult.OnSuccess -> {
                    settingsModel.value = res.settings
                    applySettings()
                }
                is SettingsStoreResult.OnError -> Log.e(TAG, res.exception.toString())
                else -> println("should not see")
            }
        }
    }

    //apply react settings that affect ui
    private fun applySettings() {
        darkMode.value = settingsModel.value.darkMode
        notifications.value = settingsModel.value.notifications
    }


    fun toggleDarkTheme() {
        settingsModel.value.darkMode = !settingsModel.value.darkMode
        setSettings()
        applySettings()
    }

    fun setNotifications(notifications: Map<NotificationLevel, Boolean>) {
        settingsModel.value.notifications = notifications
        setSettings()
        applySettings()
    }

    private fun setSettings() {
        CoroutineScope(Dispatchers.Main).launch {
            when (val res = settingsStore.setSettings(settingsModel.value)) {
                is SettingsStoreResult.OnComplete -> println("settings stored")
                is SettingsStoreResult.OnError -> Log.e(TAG, res.exception.toString())
                else -> println("should not see")
            }
        }
    }

}