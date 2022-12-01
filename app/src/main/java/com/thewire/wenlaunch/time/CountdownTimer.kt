package com.thewire.wenlaunch.ui.launch

import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.util.asUTC
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class LaunchCountdown(
    private val launchTime: ZonedDateTime,
    private val timeNow: () -> ZonedDateTime = { ZonedDateTime.now() },
    private val dispatcherProvider: IDispatcherProvider,
) {
    private val countdownInternal = MutableStateFlow(DateTimePeriod.ZERO)
    val countdown: StateFlow<DateTimePeriod> = countdownInternal

    var isStarted = false
        private set(value) {
            field = value
        }

    private val scope = CoroutineScope(dispatcherProvider.getIOContext())
    private var job: Job? = null

    fun start(): StateFlow<DateTimePeriod> {
        if (isStarted) {
            return countdown
        } else {
            isStarted = true
            startInternal()
        }
        return countdown
    }

    fun resume() {
        if (isStarted) {
            startInternal()
        }
    }

    fun pause() {
        job?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

    fun stop() {
        isStarted = false
        pause()
    }

    private fun startInternal() {
        job?.apply {
            if (isActive) return
        }
        var secondDifference = ChronoUnit.SECONDS.between(timeNow().asUTC(), launchTime.asUTC())
        job = scope.launch(dispatcherProvider.getIOContext()) {
            while (secondDifference >= 0 && isActive) {
                val now = timeNow()
                countdownInternal.value = DateTimePeriod.between(now.asUTC(), launchTime.asUTC())
                secondDifference = ChronoUnit.SECONDS.between(now, launchTime)
                delay(1000)
            }
        }
    }
}


data class TimePeriod(val totalSeconds: Long) {
    val totalMinutes = totalSeconds / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    val seconds = totalSeconds - (totalMinutes * 60)

    companion object {
        val ZERO = TimePeriod(0)
        fun fromLocalTimes(earlier: LocalTime, later: LocalTime): TimePeriod {
            val differenceSeconds = later.toSecondOfDay() - earlier.toSecondOfDay()
            val finalDifference = if (differenceSeconds < 0) {
                (later.toSecondOfDay() + 24 * 60 * 60) - earlier.toSecondOfDay()
            } else {
                differenceSeconds
            }
            return TimePeriod(finalDifference.toLong())
        }
    }

    operator fun compareTo(timePeriod: TimePeriod): Int {
        return (this.totalSeconds - timePeriod.totalSeconds).toInt()
    }
}

data class DateTimePeriod(
    val period: Period,
    val timePeriod: TimePeriod,
) {
    companion object {

        val ZERO = DateTimePeriod(Period.ZERO, TimePeriod.ZERO)

        fun between(earlier: LocalDateTime, later: LocalDateTime): DateTimePeriod {
            //what is the difference in only time
            val timePeriod =
                earlier.toLocalTime().until(later, ChronoUnit.SECONDS)
            //if earlier is later in its own day correct for this
            //this ensures that time is accounted for as well as date
            //i.e. 2023-01-1T12:00:00Z is 1 day 12 hours from "2023-01-3T00:00:00Z not 2 days"
            val adjustedPeriod = if (timePeriod < 0 && Period.between(
                    earlier.toLocalDate(),
                    later.toLocalDate()
                ).days > 0) {
                later.toLocalDate().minusDays(1)
            } else {
                later.toLocalDate()
            }
            return DateTimePeriod(
                Period.between(
                    earlier.toLocalDate(),
                    adjustedPeriod
                ),
                TimePeriod.fromLocalTimes(earlier.toLocalTime(), later.toLocalTime())
            )
        }
    }
}