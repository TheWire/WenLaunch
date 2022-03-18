package com.thewire.wenlaunch.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import java.time.temporal.ChronoUnit

class NotificationAlarmGenerator(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>) {
        notifications.forEach { (notificationLevel, on) ->
            if (on) {
                setAlarm(
                    launch,
                     notificationLevel.time * 60L,
                    notifications
                )
            }
        }
    }

    private fun setAlarm(
        launch: Launch,
        notifyTimeAheadSeconds: Long,
        notifications: Map<NotificationLevel, Boolean>
    ) {
        val alarmTime = launch.net.minus(ALARM_AHEAD + notifyTimeAheadSeconds, ChronoUnit.SECONDS)
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        intent.putExtra(ALARM_RECEIVER_LAUNCH_TIME, launch.net.toEpochSecond())
        intent.putExtra(ALARM_RECEIVER_NOTIFY_TIME, notifyTimeAheadSeconds)
        intent.putExtra(ALARM_RECEIVER_LAUNCH_ID, launch.id)
        val notificationList: List<String> = notifications.filter { it.value }.map { it.key.name }
        intent.putExtra(ALARM_RECEIVER_NOTIFICATIONS, notificationList.toTypedArray())
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                launch.id.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime.toEpochSecond(),
            pendingIntent
        )
    }

    fun cancelAlarms(launchId: String) {
        val intent = Intent(context, this::class.java)
        intent.action = ALARM_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            context, launchId.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }
}