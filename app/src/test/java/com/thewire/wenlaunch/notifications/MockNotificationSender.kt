package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.model.LaunchNotification


private const val TAG = "MOCK_NOTIFICATION"
class MockNotificationSender() : INotificationSender {
    private val Log = MockLogger()
    val notificationsSent = arrayListOf<LaunchNotification>()
    override fun sendNotification(launchNotification: LaunchNotification) {
        Log.d(TAG, launchNotification.title)
        Log.d(TAG, launchNotification.text)
        Log.d(TAG, launchNotification.time.toString())
        notificationsSent.add(launchNotification)
    }
}