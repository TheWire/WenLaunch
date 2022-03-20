package com.thewire.wenlaunch.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.util.ifEmptyNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class NotificationSender
constructor(
    private val context: Context,
) : INotificationSender {

    override fun sendNotification(
        title: String,
        icon: Int,
        text: String,
        time: Long,
    ) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(text)
            .setWhen(time)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationId = 1
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }
}