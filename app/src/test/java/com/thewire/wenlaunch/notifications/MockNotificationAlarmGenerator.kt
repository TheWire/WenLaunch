package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

class MockNotificationAlarmGenerator : INotificationAlarmGenerator {

    var alarms = arrayListOf<Launch>()

    override fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>) {
        alarms.add(launch)
    }

    override fun cancelAlarms(launchId: String) {
        alarms.forEach {
            if(it.id == launchId) {
                alarms.remove(it)
            }
        }
    }
}