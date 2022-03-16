package com.thewire.wenlaunch.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.collect
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

const val SECONDS_IN_DAY = 60L * 60L * 24L
const val ALARM_AHEAD = 60L

class NotificationWorker(
    private val ctx: Context,
    params: WorkerParameters,
    private val repository: LaunchRepository,
    private val dispatcher: IDispatcherProvider,
) : CoroutineWorker(ctx, params) {

    var alarmsSet = false
    override suspend fun doWork(): Result {
        val job = withContext(dispatcher.getIOContext()) {
            repository.upcoming(10, 0, LaunchRepositoryUpdatePolicy.NetworkPrimary)
                .collect { dataState ->
                    dataState.data?.let { launches ->
                        val notifications = inputData.keyValueMap.mapKeys { NotificationLevel.valueOf(it.key) }
                        val now = ZonedDateTime.now()
                        val launchIn24 = launches.filter { launch ->
                            ChronoUnit.SECONDS.between(now, launch.net) < SECONDS_IN_DAY
                                    && launch.status?.abbrev == LaunchStatus.GO
                        }
                        setAlarms(launchIn24, notifications)
                    }
                }
        }
        return Result.success()
    }

    private fun setAlarms(launches: List<Launch>, notifications: Map<NotificationLevel, Any>) {
        if(alarmsSet) { return }
        alarmsSet = true
        launches.forEach { launch ->
            notifications.forEach { (notificationLevel, on) ->
                if(on is Boolean && on) {
                    setAlarm(launch, notificationLevel.time)
                }
            }
        }
    }

    private fun setAlarm(launch: Launch, time: Long) {
        val alarmTime = launch.net.minus(ALARM_AHEAD + time, ChronoUnit.SECONDS)
        val intent = Intent(ctx, NotificationAlarmReceiver::class.java)
        intent.action = "sdfsdf"
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            PendingIntent.getBroadcast(ctx, requestID, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime.toEpochSecond(), pendingIntent)
    }
}

