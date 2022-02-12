package com.thewire.wenlaunch.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thewire.wenlaunch.di.IDispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Period
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class LaunchCountdown(
    private val launchTime: ZonedDateTime,
    private val timeNow: () -> ZonedDateTime = { ZonedDateTime.now() },
    private val dispatcherProvider: IDispatcherProvider,
) {

    private val scope = CoroutineScope(dispatcherProvider.getIOContext())
    fun start(): StateFlow<DateTimePeriod> {
        val countdown = MutableStateFlow(DateTimePeriod.ZERO)
        var secondDifference = ChronoUnit.SECONDS.between(timeNow(), launchTime)
        scope.launch(dispatcherProvider.getIOContext()) {
            while(secondDifference > 0) {
                val now = timeNow()
                countdown.value = DateTimePeriod(now, launchTime)
                secondDifference = ChronoUnit.SECONDS.between(now,launchTime)
                delay(1000)
            }
        }
        return countdown
    }

    fun stop() {
        scope.coroutineContext.cancelChildren()
    }
}


data class TimePeriod(val totalSeconds: Long) {
    val totalMinutes = totalSeconds / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    val seconds = totalSeconds - (totalMinutes * 60)

    companion object {
        val ZERO = TimePeriod(0)
    }

    operator fun compareTo(timePeriod: TimePeriod): Int {
        return (this.totalSeconds - timePeriod.totalSeconds).toInt()
    }
}

data class DateTimePeriod(
    val period: Period,
    val timePeriod: TimePeriod,
) {
    constructor(earlier: ZonedDateTime, later: ZonedDateTime) : this(
        period = Period.between(earlier.toLocalDate(), later.toLocalDate()),
        timePeriod = TimePeriod(earlier.toLocalTime().until(later.toLocalTime(), ChronoUnit.SECONDS))
    )
    companion object {
        val ZERO = DateTimePeriod(Period.ZERO, TimePeriod.ZERO)
    }
}
