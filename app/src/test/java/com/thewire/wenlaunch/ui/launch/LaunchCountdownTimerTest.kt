package com.thewire.wenlaunch.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.di.ProductionDispatcherProviderImpl
import com.thewire.wenlaunch.getOrAwaitValue
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
        dispatcherProvider = ProductionDispatcherProviderImpl
        scope = CoroutineScope(dispatcherProvider.getDefaultContext())
    }

    @Test
    fun `CountdownTime should return expected time difference 1`() {
        runBlocking {
            val launchTime = ZonedDateTime.parse("2022-03-02T06:06:06Z")
            val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
            val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
            scope.launch {
                val timer = launchCountdown.start()
                val ret = timer.getOrAwaitValue(1)
                compareTimePeriod(ret, 0, 0, 1, 6L, 6L, 6L)
                scope.coroutineContext.cancelChildren()
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 2`() {
        runBlocking {
            val launchTime = ZonedDateTime.parse("2022-03-01T00:00:30Z")
            val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
            val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
            scope.launch {
                val timer = launchCountdown.start()
                val ret = timer.getOrAwaitValue(1)
                compareTimePeriod(ret, 0, 0, 0, 0L, 0L, 30L)
                scope.coroutineContext.cancelChildren()
            }
        }
    }

    @Test
    fun `CountdownTime should return expected time difference 3`() {
        runBlocking {
            val launchTime = ZonedDateTime.parse("2023-09-23T23:23:23Z")
            val mockTimeNow = ZonedDateTime.parse("2022-03-01T00:00:00Z")
            val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
            scope.launch {
                val timer = launchCountdown.start()
                val ret = timer.getOrAwaitValue(1)
                compareTimePeriod(ret, 1, 6, 22, 23L, 23L, 23L)
                scope.coroutineContext.cancelChildren()
            }
        }
    }

    @Test
    fun `CountdownTime should return 0 for time in past`() {
        runBlocking {
            val launchTime = ZonedDateTime.parse("2022-03-01T00:00:00Z")
            val mockTimeNow = ZonedDateTime.parse("2022-03-01T06:06:06Z")
            val launchCountdown = LaunchCountdown(launchTime, { mockTimeNow }, dispatcherProvider)
            scope.launch {
                val timer = launchCountdown.start()
                val ret = timer.getOrAwaitValue(1)
                compareTimePeriod(ret, 0, 0, 0, 0L, 0L, 0L)
                scope.coroutineContext.cancelChildren()
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