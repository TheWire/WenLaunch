package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

interface INotificationAlarmGenerator {

    fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>)
    fun cancelAlarms(launchId: String)
}