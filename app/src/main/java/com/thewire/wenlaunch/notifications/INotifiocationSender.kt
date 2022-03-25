package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.notifications.model.LaunchNotification

interface INotificationSender {

    fun sendNotification(
        launchNotification: LaunchNotification
    )
}