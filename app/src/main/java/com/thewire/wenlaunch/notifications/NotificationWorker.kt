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

const val SECONDS_IN_DAY = 60L * 60L * 24L
const val ALARM_AHEAD = 120L

class NotificationWorker(
    ctx: Context,
    params: WorkerParameters,
    private val repository: LaunchRepository,
    private val dispatcher: IDispatcherProvider,
    private val notificationAlarmGenerator: NotificationAlarmGenerator
) : CoroutineWorker(ctx, params) {

    var alarmsSet = false

    override suspend fun doWork(): Result {
        alarmsSet = false
        withContext(dispatcher.getIOContext()) {
            repository.upcoming(10, 0, LaunchRepositoryUpdatePolicy.NetworkPrimary)
                .collect { dataState ->
                    dataState.data?.let { launches ->
                        val notifications = inputData.keyValueMap.entries.associate {
                            NotificationLevel.valueOf(it.key) to it.value as Boolean
                        }
                        val now = ZonedDateTime.now()
                        val launchIn24 = launches.filter { launch ->
                            ChronoUnit.SECONDS.between(now, launch.net) < SECONDS_IN_DAY
                                    && launch.status?.abbrev == LaunchStatus.GO
                        }
                        if(!alarmsSet) {
                            launchIn24.forEach { launch ->
                                notificationAlarmGenerator.setLaunchAlarms(launch, notifications)
                            }
                            alarmsSet = true
                        }
                    }
                }
        }
        return Result.success()
    }
}

