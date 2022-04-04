package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.model.Alarm
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.util.toEpochMilliSecond
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

private const val TAG = "MockNotificationAlarmGenerator"
const val ALARM_AHEAD = 60L

class MockNotificationAlarmGenerator(
    private val repository: ILaunchRepository
) : INotificationAlarmGenerator {

    private val Log = MockLogger()

    override suspend fun setLaunchAlarms(
        launch: Launch,
        notifications: Map<NotificationLevel, Boolean>
    ) {


        notifications.forEach {
            val alarmTime = launch.net.toEpochMilliSecond() - (it.key.time * 60000L) - (ALARM_AHEAD * 1000L)
            Log.i(TAG, "alarms set ${launch.name} at $alarmTime")
            repository.insertAlarm(
                Alarm(
                    requestId = Pair(launch.id, alarmTime).hashCode(),
                    time = alarmTime,
                    launchId = launch.id,
                )
            ).collect {}
        }
    }

    override suspend fun cancelSingleAlarm(id: Int) {
        Log.i(TAG, "alarm $id canceled")
        repository.deleteAlarm(id).collect {}
    }

    override suspend fun cancelAllAlarms() {
        Log.i(TAG, "all alarms cancelled")
        repository.deleteAllAlarms().collect {}
    }

    override suspend fun cancelAlarmsOfLaunch(launchId: String) {
        Log.i(TAG, "alarms of launch $launchId canceled")
        repository.deleteAlarmsOfLaunch(launchId).collect {}
    }
}