package com.thewire.wenlaunch.ui.launch

import com.thewire.wenlaunch.di.IDispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime
import java.time.Period
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class LaunchCountdown(
    private val launchTime: ZonedDateTime,
    private val timeNow: () -> ZonedDateTime = { ZonedDateTime.now() },
    private val dispatcherProvider: IDispatcherProvider,
) {
    private val _countdown = MutableStateFlow(DateTimePeriod.ZERO)
    val countdown: StateFlow<DateTimePeriod> = _countdown

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
            _start()
        }
        return countdown
    }

    fun resume() {
        if (isStarted) {
            _start()
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

    private fun _start() {
        job?.apply {
            if (isActive) return
        }
        var secondDifference = ChronoUnit.SECONDS.between(timeNow(), launchTime)
        job = scope.launch(dispatcherProvider.getIOContext()) {
            while (secondDifference > 0 && this.isActive) {
                val now = timeNow()
                _countdown.value = DateTimePeriod.between(now, launchTime)
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

            fun between(earlier: ZonedDateTime, later: ZonedDateTime): DateTimePeriod {
                val timePeriod =
                    earlier.toLocalTime().until(later.toLocalTime(), ChronoUnit.SECONDS)
                val adjustedPeriod = if (timePeriod < 0) {
                    later.plusSeconds(timePeriod)
                } else {
                    later
                }

                return DateTimePeriod(
                    Period.between(earlier.toLocalDate(), adjustedPeriod.toLocalDate()),
                    TimePeriod.fromLocalTimes(earlier.toLocalTime(), later.toLocalTime())
                )
            }
        }
    }
