package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.TestDispatcherProvider
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.model.Alarm
import com.thewire.wenlaunch.notifications.model.LaunchNotification
import com.thewire.wenlaunch.repository.MockRepositoryImpl
import com.thewire.wenlaunch.util.toEpochMilliSecond
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZonedDateTime

class LaunchNotificationHandlerTest {

    lateinit var notificationHandler: NotificationHandler
    lateinit var notifications: Map<NotificationLevel, Boolean>

//    @Before
//    fun setup() {
//
//    }

    @Test
    fun `notification should be issued with no change to time`() {
        val mockCurrentTime = ZonedDateTime.parse("2022-03-19T04:41:00Z")
        val alarmTime = ZonedDateTime.parse("2022-03-19T04:39:30Z")
        val notificationTime = ZonedDateTime.parse("2022-03-19T04:41:30Z")
        val launchTimeOriginal = ZonedDateTime.parse("2022-03-19T04:42:30Z")
        val launchId = "72188aca-810d-40b9-887d-43040614dd2c"
        val expectedNotification = LaunchNotification(
            title = "Falcon 9 Block 5 | Starlink Group 4-12",
            icon = R.mipmap.ic_launcher_round,
            text = "launch in 1 minute",
            time = notificationTime.toEpochMilliSecond()
        )
        val repository = MockRepositoryImpl()
        repository.insertAlarm(
            Alarm(
                requestId = Pair(launchId, alarmTime.toEpochMilliSecond()).hashCode(),
                time = alarmTime.toEpochMilliSecond(),
                launchId = launchId,
            )
        )
        val alarmGenerator = MockNotificationAlarmGenerator(repository)
        val notificationSender = MockNotificationSender()
        notificationHandler = NotificationHandler(
            repository,
            TestDispatcherProvider,
            alarmGenerator,
            notificationSender,
            MockLogger()
        ) { mockCurrentTime.toEpochMilliSecond() }
        notifications = mapOf(NotificationLevel.MINUTES1 to true)
        runBlocking {
            notificationHandler.updateAndNotify(
                "72188aca-810d-40b9-887d-43040614dd2c",
                launchTimeOriginal.toEpochMilliSecond(),
                0,
                NotificationLevel.MINUTES1,
                notifications
            )
            assert(repository.alarms.isEmpty())
            assertEquals(notificationSender.notificationsSent[0], expectedNotification)
        }
    }

    @Test
    fun `notification should be issued with change to time`() {
        val mockCurrentTime = ZonedDateTime.parse("2022-03-19T04:36:30Z")
        val alarmTime = ZonedDateTime.parse("2022-03-19T04:36:30Z")
        val notificationTime = ZonedDateTime.parse("2022-03-19T04:38:30Z")
        val launchTimeOriginal = ZonedDateTime.parse("2022-03-19T04:39:30Z")
        val launchTimeNew = ZonedDateTime.parse("2022-03-19T04:42:30Z")
        val alarmTimeNew = ZonedDateTime.parse("2022-03-19T04:40:30Z")
        val launchId = "72188aca-810d-40b9-887d-43040614dd2c"
        val repository = MockRepositoryImpl()
        val expectedAlarm = Alarm(
            requestId = Pair(launchId, alarmTimeNew.toEpochMilliSecond()).hashCode(),
            time = alarmTimeNew.toEpochMilliSecond(),
            launchId = launchId,
        )
        val alarmGenerator = MockNotificationAlarmGenerator(repository)
        val notificationSender = MockNotificationSender()
        notificationHandler = NotificationHandler(
            repository,
            TestDispatcherProvider,
            alarmGenerator,
            notificationSender,
            MockLogger()
        ) { mockCurrentTime.toEpochMilliSecond() }
        runBlocking {
            repository.insertAlarm(
                Alarm(
                    requestId = 0,
                    time = alarmTime.toEpochMilliSecond(),
                    launchId = launchId,
                )
            ).collect { }
            notifications = mapOf(NotificationLevel.MINUTES1 to true)
            notificationHandler.updateAndNotify(
                launchId,
                launchTimeOriginal.toEpochMilliSecond(),
                0,
                NotificationLevel.MINUTES1,
                notifications
            )
            println(repository.alarms)
            println(expectedAlarm)
            assertEquals(repository.alarms.size, 1)
            assert(repository.alarms.contains(expectedAlarm))
            println(notificationSender.notificationsSent)
            assert(notificationSender.notificationsSent.isEmpty())
        }
    }
}