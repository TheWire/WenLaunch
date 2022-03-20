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
import com.thewire.wenlaunch.repository.ILaunchRepository
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
    lateinit var repository: ILaunchRepository
    lateinit var notificationHandler: NotificationHandler


    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
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
                notificationHandler.updateAndNotify(id, launchTime, notificationLevel, notifications)
            }
        }
    }
}