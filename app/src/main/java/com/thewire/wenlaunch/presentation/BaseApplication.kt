package com.thewire.wenlaunch.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.work.*
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.notifications.NotificationWorker
import com.thewire.wenlaunch.repository.store.SettingsStore
import com.thewire.wenlaunch.repository.store.SettingsStoreResult
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val WORKER_TAG = "WENLAUNCH_NOTIFICATION_WORKER"
const val UNIQUE_WORK_TAG = "WENLAUNCH_UNIQUE_WORKER"

@HiltAndroidApp
class BaseApplication() : Application(), Configuration.Provider {

    @Inject
    lateinit var notificationWorkerFactory: DelegatingWorkerFactory
    @Inject
    lateinit var settingsStore: SettingsStore
    private val settingsModel: MutableState<SettingsModel> = mutableStateOf(SettingsModel())
    val notifications: MutableState<Map<NotificationLevel, Boolean>> = mutableStateOf(HashMap())
    val darkMode = mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        loadSettings()
        runNotificationWorker()
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
        runNotificationWorker()
    }

    private fun runNotificationWorker() {
        val workManager: WorkManager = WorkManager.getInstance(this)
        if(!notifications.value.containsValue(true)) {
            workManager.cancelAllWorkByTag(WORKER_TAG)
            return
        }
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val data = Data.Builder()
            .putAll(notifications.value.mapKeys {
                it.key.toString()
            })
            .build()
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInputData(data)
            .addTag(WORKER_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
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

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(notificationWorkerFactory)
            .build()
    }
}