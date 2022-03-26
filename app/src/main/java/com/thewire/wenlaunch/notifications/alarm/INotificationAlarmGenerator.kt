package com.thewire.wenlaunch.notifications.alarm

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel

interface INotificationAlarmGenerator {

    fun setLaunchAlarms(launch: Launch, notifications: Map<NotificationLevel, Boolean>)
    suspend fun cancelSingleAlarm(id: Int)
    suspend fun cancelAllAlarms()
    suspend fun cancelAlarmsOfLaunch(launchId: String)
}