package com.thewire.wenlaunch.notifications

interface INotificationSender {

    fun sendNotification(
        launchNotification: LaunchNotification
    )
}