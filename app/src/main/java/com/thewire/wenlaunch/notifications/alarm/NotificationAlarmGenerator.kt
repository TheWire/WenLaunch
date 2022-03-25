package com.thewire.wenlaunch.notifications.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.notifications.workers.ALARM_AHEAD
import com.thewire.wenlaunch.repository.ILaunchRepository
import java.time.temporal.ChronoUnit

class NotificationAlarmGenerator(
    private val context: Context,
    private val repository: ILaunchRepository
) : INotificationAlarmGenerator {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>) {
        val nowMillis = System.currentTimeMillis()
        notifications.forEach { (notificationLevel, on) ->
            if (on && isAlarmInFuture(launch, notificationLevel, nowMillis)) {
                setAlarm(
                    launch,
                    notificationLevel,
                    notifications
                )
            }
        }
    }

    private fun isAlarmInFuture(
        launch: Launch,
        notificationLevel: NotificationLevel,
        nowMillis: Long
    ): Boolean {
        val time = (launch.net.toEpochSecond() - notificationLevel.time * 60L) * 1000L
        return time < nowMillis
    }

    private fun setAlarm(
        launch: Launch,
        notificationLevel: NotificationLevel,
        notifications: Map<NotificationLevel, Boolean>
    ) {
        val alarmTime =
            launch.net.minus(ALARM_AHEAD + (notificationLevel.time * 60L), ChronoUnit.SECONDS)
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        intent.putExtra(ALARM_RECEIVER_LAUNCH_TIME, launch.net.toEpochSecond())
        intent.putExtra(ALARM_RECEIVER_NOTIFICATION_LEVEL, notificationLevel.name)
        intent.putExtra(ALARM_RECEIVER_LAUNCH_ID, launch.id)
        val notificationList: List<String> = notifications.filter { it.value }.map { it.key.name }
        intent.putExtra(ALARM_RECEIVER_NOTIFICATIONS, notificationList.toTypedArray())
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                launch.id.hashCode(),
                intent,
                0
            )
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime.toEpochSecond(),
            pendingIntent
        )
    }

    override fun cancelAlarms(launchId: String) {
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            context, launchId.hashCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}