package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.model.Alarm
import com.thewire.wenlaunch.notifications.workers.NotificationWorker
import com.thewire.wenlaunch.repository.ILaunchRepository

private const val TAG = "MockNotificationAlarmGenerator"
const val ALARM_AHEAD = 120L

class MockNotificationAlarmGenerator(
    private val repository: ILaunchRepository
) : INotificationAlarmGenerator {

    private val Log = MockLogger()

    override suspend fun setLaunchAlarms(
        launch: Launch,
        notifications: Map<NotificationLevel, Boolean>
    ) {
        Log.i(TAG, "alarms set ${launch.name}")
        notifications.forEach {
            repository.insertAlarm(
                Alarm(
                    requestId = Pair(launch.id, launch.net).hashCode(),
                    time = launch.net.toEpochSecond() - ALARM_AHEAD,
                    launchId = launch.id,
                )
            )
        }
    }

    override suspend fun cancelSingleAlarm(id: Int) {
        Log.i(TAG, "alarm $id canceled")
        repository.deleteAlarm(id)
    }

    override suspend fun cancelAllAlarms() {
        Log.i(TAG, "all alarms cancelled")
        repository.deleteAllAlarms()
    }

    override suspend fun cancelAlarmsOfLaunch(launchId: String) {
        Log.i(TAG, "alarms of launch $launchId canceled")
        repository.alarmsOfLaunch(launchId)
    }
}