package com.thewire.wenlaunch.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.repository.store.SettingsStore
import com.thewire.wenlaunch.repository.store.SettingsStoreResult
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class BaseApplication : Application() {
    @Inject
    lateinit var settingsStore : SettingsStore
    val settingsModel :MutableState<SettingsModel> = mutableStateOf(SettingsModel())
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

    private fun applySettings() {
        darkMode.value = settingsModel.value.darkMode
    }


    fun toggleDarkTheme() {
        settingsModel.value.darkMode = !settingsModel.value.darkMode
        setSettings()
        applySettings()
    }

    private fun setSettings() {
        CoroutineScope(Dispatchers.Main).launch {
            when(val res = settingsStore.setSettings(settingsModel.value)) {
                is SettingsStoreResult.OnComplete -> println("settings stored")
                is SettingsStoreResult.OnError -> Log.e(TAG, res.exception.toString())
                else -> println("should not see")
            }
        }
    }

}