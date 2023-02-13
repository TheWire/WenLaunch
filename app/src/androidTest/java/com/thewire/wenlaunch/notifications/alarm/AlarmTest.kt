package com.thewire.wenlaunch.notifications.alarm

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AlarmTest {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//    val alarmGenerator = NotificationAlarmGenerator(
//        context = appContext,
//        repository = MockRepositoryImpl()
//    )
    @Test
    fun useAppContext() {
        // Context of the app under test.

        assertEquals("com.TheWire.wenlaunch", appContext.packageName)
    }
}