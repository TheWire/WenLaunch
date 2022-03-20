package com.thewire.wenlaunch.notifications
import com.thewire.wenlaunch.di.ProductionDispatcherProviderImpl
import com.thewire.wenlaunch.repository.MockRepositoryImpl
import org.junit.Before
import org.junit.Test

class NotificationHandlerTest {

    lateinit var notificationHandler: NotificationHandler
    lateinit var notifications :Map<NotificationLevel, Boolean>

    @Before
    fun setup() {
        notificationHandler = NotificationHandler(
            MockRepositoryImpl(),
            ProductionDispatcherProviderImpl,
            MockNotificationAlarmGenerator(),
            MockNotificationSender(),
        )
        notifications = NotificationLevel.values().associate {
            it to true
        }
    }

    @Test
    fun `Notification alarm receiver test`() {
        notificationHandler.updateAndNotify(
            "72188aca-810d-40b9-887d-43040614dd2c",
            0,
            NotificationLevel.MINUTES1,
            notifications
        )
    }



}