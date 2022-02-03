package com.thewire.wenlaunch.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thewire.wenlaunch.di.IDispatcherProvider
import kotlinx.coroutines.*
import java.time.Period
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class LaunchCountdown(
    private val launchTime: ZonedDateTime,
    private val timeNow: () -> ZonedDateTime = { ZonedDateTime.now() },
    private val dispatcherProvider: IDispatcherProvider,
) {

    suspend fun start(): LiveData<DateTimePeriod> {
        val countdown = MutableLiveData<DateTimePeriod>()
        var secondDifference = ChronoUnit.SECONDS.between(timeNow(), launchTime)
        countdown.postValue(DateTimePeriod(timeNow(), launchTime))
        withContext(dispatcherProvider.getIOContext()) {
            while(secondDifference > 0) {
                delay(1000)
                val now = timeNow()
                countdown.postValue(DateTimePeriod(now, launchTime))
                secondDifference = ChronoUnit.SECONDS.between(now,launchTime)
            }
        }
        return countdown
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
