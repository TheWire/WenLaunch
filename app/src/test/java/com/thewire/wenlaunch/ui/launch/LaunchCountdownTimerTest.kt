package com.thewire.wenlaunch.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.di.TestDispatcherProvider
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

class LaunchCountdownTimerTest {

    @get:Rule
    val instanceExecutorRule = InstantTaskExecutorRule()

    lateinit var dispatcherProvider: IDispatcherProvider
    lateinit var scope: CoroutineScope

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider
        scope = CoroutineScope(dispatcherProvider.getDefaultContext())
    }

    @Test
    fun `CountdownTime should return expected time difference 1`() {
        val launchTime = ZonedDateTime.parse("2022-03-02T06:06:06Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 1, 6L, 6L, 6L)
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 2`() {
        val launchTime = ZonedDateTime.parse("2022-03-01T00:00:30Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 0L, 0L, 30L)
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 3`() {
        val launchTime = ZonedDateTime.parse("2023-09-23T23:23:23Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 1, 6, 22, 23L, 23L, 23L)
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 4`() {
        val launchTime = ZonedDateTime.parse("2022-02-14T00:55:00Z")
        val mockTimeNow = ZonedDateTime.parse("2022-02-12T16:52:23Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 1, 8L, 2L, 37L)
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 5`() {
        val launchTime = ZonedDateTime.parse("2022-03-31T13:30:00Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-31T14:20:01+01:00[Europe/London]")
        println(launchTime.toLocalDateTime())
        println(mockTimeNow.toLocalDateTime())
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 0, 9L, 59L)
            }
        }
    }

    @Test
    fun `CountdownTime should return time difference 6`() {
        val launchTime = ZonedDateTime.parse("2022-11-30T08:30:11Z")
        val mockTimeNow = ZonedDateTime.parse("2022-11-29T15:40:20Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 16L, 49L, 51L)
            }
        }
    }

    @Test
    fun `CountdownTime should return 0 for time in past 1`() {
        val launchTime = ZonedDateTime.parse("2022-03-01T00:00:00Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-01T06:06:06Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 0L, 0L, 0L)
            }
        }
    }

    @Test
    fun `CountdownTime should return 0 for time in past 2`() {
        val launchTime = ZonedDateTime.parse("2022-03-31T13:30:00Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-31T13:40:00Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 0L, 0L, 0L)
            }
        }
    }

    @Test
    fun `CountdownTime should return 0 for equal times`() {
        val launchTime = ZonedDateTime.parse("2022-03-31T13:30:00Z")
        val mockTimeNow = ZonedDateTime.parse("2022-03-31T13:30:00Z")
        val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
        runBlocking {
            val timer = launchCountdown.start()
            timer.test {
                compareTimePeriod(this.awaitItem(), 0, 0, 0, 0L, 0L, 0L)
            }
        }
    }

    private fun compareTimePeriod(
        dateTimePeriod: DateTimePeriod,
        expectedYear: Int,
        expectedMonths: Int,
        expectedDays: Int,
        expectedHours: Long,
        expectedMinutes: Long,
        expectedSeconds: Long
    ) {
        assertEquals(expectedYear, dateTimePeriod.period.years)
        assertEquals(expectedMonths, dateTimePeriod.period.months)
        assertEquals(expectedDays, dateTimePeriod.period.days)
        assertEquals(expectedHours, dateTimePeriod.timePeriod.hours)
        assertEquals(expectedMinutes, dateTimePeriod.timePeriod.minutes)
        assertEquals(expectedSeconds, dateTimePeriod.timePeriod.seconds)
    }

}