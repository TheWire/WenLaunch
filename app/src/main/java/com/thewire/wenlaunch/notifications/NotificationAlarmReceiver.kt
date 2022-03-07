package com.thewire.wenlaunch.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thewire.wenlaunch.R

const val CHANNEL_ID: String = TODO()
const val TITLE_STRING = "LAUNCH_ALARM_TITLE_STRING"
const val TEXT_STRING = "LAUNCH_ALARM_TEXT_STRING"

class NotificationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {

                val title = intent.getStringExtra(TITLE_STRING)
                val text = intent.getStringExtra(TEXT_STRING)
                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                with(NotificationManagerCompat.from(context)) {
                    notify(notificationId, notification)
                }
            }
        }

    }
}