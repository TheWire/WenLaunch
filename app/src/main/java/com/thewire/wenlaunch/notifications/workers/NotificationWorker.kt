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
import com.thewire.wenlaunch.util.SECONDS_IN_DAY
import com.thewire.wenlaunch.util.asUTC
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

private const val TAG = "LAUNCH_NOTIFICATION_WORKER"

class NotificationWorker(
    ctx: Context,
    params: WorkerParameters,
    private val repository: ILaunchRepository,
    private val dispatcher: IDispatcherProvider,
    private val notificationAlarmGenerator: INotificationAlarmGenerator,
    private val logger: ILogger
) : CoroutineWorker(ctx, params) {

    var alarmsSet = false

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
                        val now = ZonedDateTime.now()
                        val launchIn24 = launches.filter { launch ->
                            ChronoUnit.SECONDS.between(
                                now.asUTC(),
                                launch.net.asUTC()
                            ) < SECONDS_IN_DAY
                                    && launch.status?.abbrev == LaunchStatus.GO ||
                                    launch.status?.abbrev == LaunchStatus.HOLD
                        }
                        if (!alarmsSet) {
                            launchIn24.forEach { launch ->
                                logger.v(TAG, "alarm scheduled ${launch.name} ${launch.net}")
                                notificationAlarmGenerator.setLaunchAlarms(launch, notifications)
                            }
                            alarmsSet = true
                        }
                    }
                    dataState.error?.let {
                        logger.e(TAG, "error $it")
                    }
                }
        }
        return Result.success()
    }
}

