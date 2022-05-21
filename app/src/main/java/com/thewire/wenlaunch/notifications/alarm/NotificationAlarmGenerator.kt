package com.thewire.wenlaunch.notifications.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.notifications.model.Alarm
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.util.toEpochMilliSecond
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

private const val TAG = "LAUNCH_NOTIFICATION_ALARM_GENERATOR"
const val ALARM_AHEAD = 8L

class NotificationAlarmGenerator(
    private val context: Context,
    private val repository: ILaunchRepository,
    private val dispatcher: IDispatcherProvider,
    private val logger: ILogger,
) : INotificationAlarmGenerator {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun setLaunchAlarms(
        launch: Launch,
        notifications: Map<NotificationLevel, Boolean>
    ) {
        logger.i(TAG, "settings alarms for launch ${launch.id}")
        val now = ZonedDateTime.now().toEpochSecond()
        notifications.forEach { (notificationLevel, on) ->
            if (on && isAlarmInFuture(
                    launch.net.toEpochSecond(), notificationLevel, now
                )
            ) {
                logger.v(TAG, "setting ${launch.id} ${notificationLevel.name}")
                setAlarm(
                    launch,
                    notificationLevel,
                    notifications
                )
            }
        }
    }

    private fun isAlarmInFuture(
        net: Long,
        notificationLevel: NotificationLevel,
        now: Long
    ): Boolean {
        val time = (net - notificationLevel.time * 60L)
        return time > now
    }

    private suspend fun setAlarm(
        launch: Launch,
        notificationLevel: NotificationLevel,
        notifications: Map<NotificationLevel, Boolean>
    ) {
        val alarmTime =
            launch.net.minus(
                ALARM_AHEAD +
                        (notificationLevel.time * 60L), ChronoUnit.SECONDS
            ).toEpochMilliSecond()
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        intent.putExtra(ALARM_RECEIVER_LAUNCH_TIME, launch.net.toEpochMilliSecond())
        intent.putExtra(ALARM_RECEIVER_NOTIFICATION_LEVEL, notificationLevel.name)
        intent.putExtra(ALARM_RECEIVER_LAUNCH_ID, launch.id)
        val requestId = Pair(launch.id, alarmTime).hashCode()
        intent.putExtra(ALARM_REQUEST_ID, requestId)
        val notificationList: List<String> = notifications.filter { it.value }.map { it.key.name }
        intent.putExtra(ALARM_RECEIVER_NOTIFICATIONS, notificationList.toTypedArray())
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestId,
                intent,
                0
            )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent
        )
        withContext(dispatcher.getIOContext()) {
            repository.insertAlarm(
                Alarm(
                    requestId = requestId,
                    time = alarmTime,
                    launchId = launch.id,
                )
            ).collect {}
        }
    }

    override suspend fun cancelSingleAlarm(id: Int) {
        cancelAlarm(id)
        withContext(dispatcher.getIOContext()) {
            repository.deleteAlarm(id).collect { deleteResult ->
                deleteResult.error?.let {
                    logger.e(TAG, it)
                }
            }
        }
    }

    override suspend fun cancelAllAlarms() {
        withContext(dispatcher.getIOContext()) {
            repository.alarmsAll().collect { datastate ->
                datastate.data?.let { alarms ->
                    alarms.forEach { alarm ->
                        withContext(dispatcher.getMainContext()) {
                            cancelAlarm(alarm.requestId)
                        }
                    }
                    repository.deleteAllAlarms().collect { deleteResult ->
                        deleteResult.error?.let {
                            logger.e(TAG, it)
                        }
                    }
                }
            }
        }
    }

    override suspend fun cancelAlarmsOfLaunch(launchId: String) {
        withContext(dispatcher.getIOContext()) {
            repository.alarmsOfLaunch(launchId).collect { datastate ->
                datastate.data?.let { alarms ->
                    alarms.forEach { alarm ->
                        withContext(dispatcher.getMainContext()) {
                            cancelAlarm(alarm.requestId)
                        }
                    }
                    repository.deleteAlarmsOfLaunch(launchId).collect { deleteResult ->
                        deleteResult.error?.let {
                            logger.e(TAG, it)
                        }
                    }
                }
            }
        }
    }

    private fun cancelAlarm(requestId: Int) {
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestId, intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}