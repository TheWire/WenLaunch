package com.thewire.wenlaunch.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thewire.wenlaunch.notifications.alarm.NOTIFICATION_CHANNEL_ID
import com.thewire.wenlaunch.notifications.model.LaunchNotification
import com.thewire.wenlaunch.presentation.NOTIFICATION_IMPORTANCE

class NotificationSender
constructor(
    private val context: Context,
) : INotificationSender {

    override fun sendNotification(
        launchNotification: LaunchNotification
    ) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(launchNotification.icon)
            .setContentTitle(launchNotification.title)
            .setContentText(launchNotification.text)
            .setWhen(launchNotification.time)
            .setPriority(
                if(NOTIFICATION_IMPORTANCE == "HIGH") {
                    NotificationCompat.PRIORITY_HIGH
                } else NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationId = 1
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }
}