package com.thewire.wenlaunch.notifications.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import com.thewire.wenlaunch.util.asUTC
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

private const val TAG = "LAUNCH_NOTIFICATION_WORKER"
private const val SECS_IN_HOURS = 3600L

class NotificationWorker(
    ctx: Context,
    params: WorkerParameters,
    private val repository: ILaunchRepository,
    private val dispatcher: IDispatcherProvider,
    private val notificationAlarmGenerator: INotificationAlarmGenerator,
    private val logger: ILogger,
    private val timePeriodHours: Long
) : CoroutineWorker(ctx, params) {

    var alarmsSet = false

    private val timePeriodSeconds = timePeriodHours * SECS_IN_HOURS

    override suspend fun doWork(): Result {
        logger.i(TAG, "notification worker started")
        alarmsSet = false
        withContext(dispatcher.getIOContext()) {
            repository.upcoming(10, 0, LaunchRepositoryUpdatePolicy.NetworkPrimary)
                .collect { dataState ->
                    dataState.data?.let { launches ->
                        val notifications = inputData.keyValueMap.entries.associate {
                            NotificationLevel.valueOf(it.key) to it.value as Boolean
                        }

                        notificationAlarmGenerator.cancelAllAlarms()

                        val now = ZonedDateTime.now()

                        if (!alarmsSet) {
                            launches.forEach { launch ->
                                 val alarmsIn24 = notifications.filter { level ->
                                     ChronoUnit.SECONDS.between(
                                         now.asUTC(),
                                         launch.net.asUTC()
                                     ) < timePeriodSeconds + (level.key.time * 60L) && level.value
                                             && launch.status?.abbrev == LaunchStatus.GO ||
                                             launch.status?.abbrev == LaunchStatus.HOLD
                                }
                                if(alarmsIn24.isNotEmpty()) {
                                    logger.v(TAG, "alarm scheduled ${launch.name} ${launch.net}")
                                    notificationAlarmGenerator.setLaunchAlarms(launch, alarmsIn24)
                                }
                            }
                        }
                    }
                    dataState.error?.let {
                        logger.e(TAG, "error $it")
                    }
                }
        }
        return Result.success()
    }

    //check launch is go/hold and the alarm needs to be issued
    //before next NotificationWorker runs.
    private fun launchAlarmInTimePeriod(
        currentTime: ZonedDateTime,
        launchTime: ZonedDateTime,
        workerTimePeriodSecs: Long,
        launchStatus: LaunchStatus,
        notificationTimeSecs: Long
    ): Boolean {
        return (ChronoUnit.SECONDS.between(
            currentTime.asUTC(),
            launchTime.asUTC()
        ) < workerTimePeriodSecs + notificationTimeSecs
                && launchStatus == LaunchStatus.GO ||
                launchStatus == LaunchStatus.HOLD)
    }
}

