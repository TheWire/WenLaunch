package com.thewire.wenlaunch.presentation

import android.Manifest
import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.work.*
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.di.NOTIFICATION_WORKER_TIME_PERIOD_HOURS
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.alarm.NOTIFICATION_CHANNEL_ID
import com.thewire.wenlaunch.notifications.workers.NotificationWorker
import com.thewire.wenlaunch.repository.store.SettingsStore
import com.thewire.wenlaunch.repository.store.SettingsStoreResult
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val WORKER_TAG = "WENLAUNCH_NOTIFICATION_WORKER"
const val UNIQUE_WORK_TAG = "WENLAUNCH_UNIQUE_WORKER"

const val NOTIFICATION_IMPORTANCE = "HIGH"
private const val PERMISSION_REQUEST_CODE = 1

@HiltAndroidApp
class BaseApplication() : Application(), Configuration.Provider {

    @Inject
    lateinit var dispatcher: IDispatcherProvider
    @Inject
    lateinit var notificationWorkerFactory: DelegatingWorkerFactory
    @Inject
    lateinit var alarmGenerator: INotificationAlarmGenerator
    @Inject
    lateinit var settingsStore: SettingsStore
    private val settingsModel: MutableState<SettingsModel> = mutableStateOf(SettingsModel())
    val notifications: MutableState<Map<NotificationLevel, Boolean>> = mutableStateOf(HashMap())
    val darkMode = mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        loadSettings()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val priority = if (NOTIFICATION_IMPORTANCE == "HIGH") {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            NotificationManager.IMPORTANCE_DEFAULT
        }
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "WenLaunch",
            priority,
        ).apply {
            description = "Launch notifications"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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
        runNotificationWorker()
    }


    fun toggleDarkTheme() {
        settingsModel.value.darkMode = !settingsModel.value.darkMode
        setSettings()
        applySettings()
    }

    fun setNotifications(notifications: Map<NotificationLevel, Boolean>, activity: Activity?) {
        settingsModel.value.notifications = notifications
        setSettings()
        if (notifications.containsValue(true) && activity != null) {
            permissions(activity)
        }
        applySettings()


    }

    private fun permissions(activity: Activity) {
        if(Build.VERSION.SDK_INT >= 33) {
            val notificationPermission =
                ActivityCompat.checkSelfPermission(
                    activity.baseContext,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            val alarmPermission =
                ActivityCompat.checkSelfPermission(activity.baseContext, Manifest.permission.SET_ALARM)
            if (notificationPermission != PackageManager.PERMISSION_GRANTED || alarmPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.SET_ALARM
                    ),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun runNotificationWorker() {

        CoroutineScope(dispatcher.getIOContext()).launch {

            withContext(dispatcher.getMainContext()) {
                val workManager: WorkManager = WorkManager.getInstance(this@BaseApplication)
                if (!notifications.value.containsValue(true)) {
                    println("cancel notifications")
                    workManager.cancelAllWorkByTag(WORKER_TAG)
                    alarmGenerator.cancelAllAlarms()
                } else {
                    val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                    val data = Data.Builder()
                        .putAll(notifications.value.mapKeys {
                            it.key.toString()
                        })
                        .build()
                    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
                        repeatInterval = NOTIFICATION_WORKER_TIME_PERIOD_HOURS,
                        repeatIntervalTimeUnit = TimeUnit.HOURS
                    )
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
            }
        }
    }

    private fun setSettings() {
        CoroutineScope(dispatcher.getMainContext()).launch {
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

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}