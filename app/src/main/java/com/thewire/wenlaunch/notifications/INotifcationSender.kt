package com.thewire.wenlaunch.notifications

interface INotificationSender {

    fun sendNotification(
        title: String,
        icon: Int,
        text: String,
        time: Long,
    )
}