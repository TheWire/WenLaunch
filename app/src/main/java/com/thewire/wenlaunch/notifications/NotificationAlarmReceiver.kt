package com.thewire.wenlaunch.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import com.thewire.wenlaunch.util.ifEmptyNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val ALARM_ACTION = "com.thewire.wenlaunch.NotificationAlarm"
const val NOTIFICATION_CHANNEL_ID: String = "com.thewire.wenlaunch.NOTIFICATION_CHANNEL"
const val ALARM_RECEIVER_LAUNCH_TIME = "ALARM_RECEIVER_LAUNCH_TIME"
const val ALARM_RECEIVER_NOTIFICATION_LEVEL = "ALARM_RECEIVER_NOTIFICATION_LEVEL"
const val ALARM_RECEIVER_LAUNCH_ID = "LAUNCH_ID"
const val ALARM_RECEIVER_NOTIFICATIONS = "NOTIFICATIONS"
const val ALARM_RECEIVER_LAUNCH_MARGIN = 60L

@AndroidEntryPoint
class NotificationAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: LaunchRepository
    lateinit var dispatcherProvider: IDispatcherProvider
    lateinit var notificationAlarmGenerator: NotificationAlarmGenerator


    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val launchId = intent.getStringExtra(ALARM_RECEIVER_LAUNCH_ID)
            val launchTime = intent.getLongExtra(ALARM_RECEIVER_LAUNCH_TIME, 0)
            val notificationLevel =
                intent.getStringExtra(ALARM_RECEIVER_NOTIFICATION_LEVEL)?.let { level ->
                    NotificationLevel.valueOf(level)
                } ?: NotificationLevel.DEFAULT

            val notifications =
                intent.getStringArrayListExtra(ALARM_RECEIVER_NOTIFICATIONS)?.associate {
                    NotificationLevel.valueOf(it) to true
                }
            launchId?.let { id ->
                updateAndNotify(context, id, launchTime, notificationLevel, notifications)
            }
        }
    }

    private fun updateAndNotify(
        context: Context,
        id: String,
        launchTime: Long,
        notificationLevel: NotificationLevel,
        notifications: Map<NotificationLevel, Boolean>?
    ) {
        CoroutineScope(dispatcherProvider.getIOContext()).launch {
            repository.launch(
                id,
                LaunchRepositoryUpdatePolicy.NetworkPrimary
            ).collect { dataState ->
                dataState.data?.let { launch ->
                    if (launch.status?.abbrev == LaunchStatus.GO) {
                        val launchTimeNew = launch.net.toEpochSecond()
                        if (launchTimeNew + ALARM_RECEIVER_LAUNCH_MARGIN > launchTime &&
                            launchTimeNew - ALARM_RECEIVER_LAUNCH_MARGIN < launchTime
                        ) {
                            sendNotification(context, launch, notificationLevel)
                        } else {
                            notificationAlarmGenerator.cancelAlarms(launch.id)
                            notifications?.let {
                                notificationAlarmGenerator.setLaunchAlarms(launch, it)
                            }
                        }
                    } else {
                        notificationAlarmGenerator.cancelAlarms(launch.id)
                    }
                }
            }
        }
    }

    private suspend fun sendNotification(
        context: Context,
        launch: Launch,
        notificationLevel: NotificationLevel
    ) {
        val notifyTime = launch.net.toEpochSecond() - (notificationLevel.time * 60L)
        val text = when (notificationLevel) {
            NotificationLevel.DEFAULT,
            NotificationLevel.WEBCAST,
            NotificationLevel.LAUNCH -> notificationLevel.description
            else -> "launch in ${notificationLevel.description}"
        }
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(launch.name.ifEmptyNull() ?: "Unknown Launch")
            .setContentText(text)
            .setWhen(notifyTime)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationId = 1
        withContext(dispatcherProvider.getDefaultContext()) {
            val delayAmount = maxOf((notifyTime * 1000) - System.currentTimeMillis(), 0)
            delay(delayAmount)
        }
        withContext(dispatcherProvider.getMainContext()) {
            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, notification)
            }
        }
    }
}