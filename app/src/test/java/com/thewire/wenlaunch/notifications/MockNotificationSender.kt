package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.logging.MockLogger
import org.junit.Assert.assertEquals


const val TAG = "MOCK_NOTIFICATION"
class MockNotificationSender(
    val expectedLaunchNotification: LaunchNotification
) : INotificationSender {
    val Log = MockLogger()
    override fun sendNotification(launchNotification: LaunchNotification) {
        Log.d(TAG, launchNotification.title)
        Log.d(TAG, launchNotification.text)
        Log.d(TAG, launchNotification.time.toString())
        assertEquals(launchNotification, expectedLaunchNotification)
    }
}