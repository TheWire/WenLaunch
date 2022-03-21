package com.thewire.wenlaunch.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

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
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationId = 1
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }
}