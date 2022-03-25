package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator

private const val TAG = "MockNotificationAlarmGenerator"

class MockNotificationAlarmGenerator() : INotificationAlarmGenerator {

    private val Log = MockLogger()

    var cancel = false
    var alarms = arrayListOf<Launch>()

    override fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>) {
        Log.i(TAG, "alarms set ${launch.name}")
        alarms.add(launch)
    }

    override fun cancelAlarms(launchId: String) {
        Log.i(TAG, "alarms canceled")
        cancel = true
        alarms.forEach {
            if(it.id == launchId) {
                alarms.remove(it)
            }
        }
    }
}