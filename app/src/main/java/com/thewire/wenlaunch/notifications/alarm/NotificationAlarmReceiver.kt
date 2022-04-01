package com.thewire.wenlaunch.notifications.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.notifications.NotificationHandler
import com.thewire.wenlaunch.repository.ILaunchRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ALARM_ACTION = "com.thewire.wenlaunch.NotificationAlarm"
const val NOTIFICATION_CHANNEL_ID: String = "com.thewire.wenlaunch.NOTIFICATION_CHANNEL"
const val ALARM_RECEIVER_LAUNCH_TIME = "ALARM_RECEIVER_LAUNCH_TIME"
const val ALARM_REQUEST_ID = "ALARM_REQUEST_ID"
const val ALARM_RECEIVER_NOTIFICATION_LEVEL = "ALARM_RECEIVER_NOTIFICATION_LEVEL"
const val ALARM_RECEIVER_LAUNCH_ID = "LAUNCH_ID"
const val ALARM_RECEIVER_NOTIFICATIONS = "NOTIFICATIONS"
const val ALARM_RECEIVER_LAUNCH_MARGIN = 60L

private const val TAG = "NOTIFICATION_ALARM_RECEIVER"

@AndroidEntryPoint
class NotificationAlarmReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: ILaunchRepository
    @Inject lateinit var notificationHandler: NotificationHandler
    @Inject lateinit var dispatcher: IDispatcherProvider
    @Inject lateinit var Log: ILogger


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "alarm received")
        if (intent != null) {
            val launchId = intent.getStringExtra(ALARM_RECEIVER_LAUNCH_ID)
            val launchTime = intent.getLongExtra(ALARM_RECEIVER_LAUNCH_TIME, 0)
            val requestId = intent.getIntExtra(ALARM_REQUEST_ID, 0)
            val notificationLevel =
                intent.getStringExtra(ALARM_RECEIVER_NOTIFICATION_LEVEL)?.let { level ->
                    NotificationLevel.valueOf(level)
                } ?: NotificationLevel.DEFAULT

            Log.i(TAG, "$launchId, $launchTime, $requestId, ${notificationLevel.name}")
            val notifications =
                intent.getStringArrayListExtra(ALARM_RECEIVER_NOTIFICATIONS)?.associate {
                    NotificationLevel.valueOf(it) to true
                }
            launchId?.let { id ->
                CoroutineScope(dispatcher.getMainContext()).launch {
                    notificationHandler.updateAndNotify(
                        id,
                        launchTime,
                        requestId,
                        notificationLevel,
                        notifications,
                    )
                }
            }
        }
    }
}