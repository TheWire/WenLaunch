package com.thewire.wenlaunch.notifications

import android.util.Log

const val TAG = "MOCK_NOTIFICATION"
class MockNotificationSender : INotificationSender {
    override fun sendNotification(title: String, icon: Int, text: String, time: Long) {
        Log.d(TAG, title)
        Log.d(TAG, text)
        Log.d(TAG, time.toString())
    }
}