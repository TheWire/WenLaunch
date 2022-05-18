package com.thewire.wenlaunch.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.notifications.alarm.NOTIFICATION_CHANNEL_ID
import com.thewire.wenlaunch.notifications.model.LaunchNotification
import com.thewire.wenlaunch.presentation.NOTIFICATION_IMPORTANCE

private const val TAG = "LAUNCH_NOTIFICATION_SENDER"

class NotificationSender
constructor(
    private val context: Context,
    private val logger: ILogger,
) : INotificationSender {

    override fun sendNotification(
        launchNotification: LaunchNotification
    ) {
        val priority =  if(NOTIFICATION_IMPORTANCE == "HIGH") {
            NotificationCompat.PRIORITY_HIGH
        } else NotificationCompat.PRIORITY_DEFAULT

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(launchNotification.icon)
            .setContentTitle(launchNotification.title)
            .setContentText(launchNotification.text)
            .setWhen(launchNotification.time)
            .setPriority(priority)
            .build()

        val notificationId = 1
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }

        logger.v(
            TAG,
            "Notification sent ${launchNotification.title} ${launchNotification.text} " +
                    "${launchNotification.time}"
        )
    }
}