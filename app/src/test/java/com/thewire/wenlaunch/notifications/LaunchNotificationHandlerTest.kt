package com.thewire.wenlaunch.notifications
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.TestDispatcherProvider
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.model.LaunchNotification
import com.thewire.wenlaunch.repository.MockRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZonedDateTime

class LaunchNotificationHandlerTest {

    lateinit var notificationHandler: NotificationHandler
    lateinit var notifications :Map<NotificationLevel, Boolean>

//    @Before
//    fun setup() {
//
//    }

    @Test
    fun `notification should be issued with no change to time`() {
        val expectedNotification = LaunchNotification(
            title = "Falcon 9 Block 5 | Starlink Group 4-12",
            icon = R.mipmap.ic_launcher_round,
            text = "launch in 1 minute",
            time = ZonedDateTime.parse("2022-03-19T04:41:30Z").toEpochSecond()
        )
        val alarmGenerator = MockNotificationAlarmGenerator()
        notificationHandler = NotificationHandler(
            MockRepositoryImpl(),
            TestDispatcherProvider,
            alarmGenerator,
            MockNotificationSender(expectedNotification),
            MockLogger()
        ) { ZonedDateTime.parse("2022-03-19T04:41:00Z").toEpochSecond() * 1000 }
        notifications = mapOf(NotificationLevel.MINUTES1 to true)
        runBlocking {
            notificationHandler.updateAndNotify(
                "72188aca-810d-40b9-887d-43040614dd2c",
                ZonedDateTime.parse("2022-03-19T04:42:30Z").toEpochSecond(),
                NotificationLevel.MINUTES1,
                notifications
            )
            assert(!alarmGenerator.cancel)
            assert(alarmGenerator.alarms.isEmpty())
        }
    }

    @Test
    fun `notification should be issued with change to time`() {
        val expectedNotification = LaunchNotification(
            title = "Falcon 9 Block 5 | Starlink Group 4-12",
            icon = R.mipmap.ic_launcher_round,
            text = "launch in 1 minute",
            time = ZonedDateTime.parse("2022-03-19T04:41:30Z").toEpochSecond()
        )
        val alarmGenerator = MockNotificationAlarmGenerator()
        notificationHandler = NotificationHandler(
            MockRepositoryImpl(),
            TestDispatcherProvider,
            alarmGenerator,
            MockNotificationSender(expectedNotification),
            MockLogger()
        ) { ZonedDateTime.parse("2022-03-19T04:41:00Z").toEpochSecond() * 1000 }
        notifications = mapOf(NotificationLevel.MINUTES1 to true)
        runBlocking {
            notificationHandler.updateAndNotify(
                "72188aca-810d-40b9-887d-43040614dd2c",
                ZonedDateTime.parse("2022-03-19T04:44:00Z").toEpochSecond(),
                NotificationLevel.MINUTES1,
                notifications
            )
            assert(alarmGenerator.cancel)
            assertEquals(alarmGenerator.alarms[0].id, "72188aca-810d-40b9-887d-43040614dd2c")
        }
    }
}