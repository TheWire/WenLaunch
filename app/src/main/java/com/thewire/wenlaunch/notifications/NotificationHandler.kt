package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.notifications.alarm.ALARM_RECEIVER_LAUNCH_MARGIN
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.model.LaunchNotification
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import com.thewire.wenlaunch.util.ifEmptyNull
import com.thewire.wenlaunch.util.toEpochMilliSecond
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

private const val TAG = "LAUNCH_NOTIFICATION_HANDLER"

class NotificationHandler(
    private val repository: ILaunchRepository,
    private val dispatcherProvider: IDispatcherProvider,
    private val notificationAlarmGenerator: INotificationAlarmGenerator,
    private val notificationSender: INotificationSender,
    private val logger: ILogger,
    private val timeProviderMillis: () -> Long,
) {

    suspend fun updateAndNotify(
        id: String,
        launchTime: Long,
        requestId: Int,
        notificationLevel: NotificationLevel,
        notifications: Map<NotificationLevel, Boolean>?
    ) {
        logger.i(TAG, "launch $id at $launchTime ${notificationLevel.name}")
        withContext(dispatcherProvider.getIOContext()) {
            repository.launch(
                id,
                LaunchRepositoryUpdatePolicy.NetworkPrimary
            ).collect { dataState ->
                dataState.data?.let { launch ->
                    val launchTimeNew = launch.net.toEpochMilliSecond()
                    val timeChanged = hasAlarmTimeChanged(
                        launchTime, launchTimeNew, ALARM_RECEIVER_LAUNCH_MARGIN
                    )
                    logger.v(TAG, "api launch details $launchTimeNew ${launch.status}")
                    if(timeChanged) {
                        if (launch.status?.abbrev == LaunchStatus.GO ||
                            launch.status?.abbrev == LaunchStatus.HOLD) {
                            logger.i(TAG, "launch $id at $launchTime changed to $launchTimeNew")
                            notificationAlarmGenerator.cancelAlarmsOfLaunch(launch.id)
                            notifications?.let {
                                notificationAlarmGenerator.setLaunchAlarms(launch, it)
                            }
                        } else {
                            cancelAlarms(launch, id, launchTime, notificationLevel)
                        }
                    } else {
                        when(launch.status?.abbrev) {
                            LaunchStatus.GO -> {
                                sendNotification(launch, notificationLevel)
                                notificationAlarmGenerator.cancelSingleAlarm(requestId)
                            }
                            LaunchStatus.HOLD -> {
                                sendNotification(launch, notificationLevel, "Holding")
                                notificationAlarmGenerator.cancelSingleAlarm(requestId)
                            }
                            else -> {
                                cancelAlarms(launch, id, launchTime, notificationLevel)
                            }
                        }
                    }
                    dataState.error?.let {
                        logger.e(TAG, "error $it")
                    }
                }
            }
        }
    }

    private suspend fun cancelAlarms(
        launch: Launch,
        id: String,
        originalLaunchTime: Long,
        notificationLevel: NotificationLevel
    ) {
        logger.i(
            TAG, "launch $id at $originalLaunchTime " +
                    "${notificationLevel.name} changed to " +
                    "${launch.status?.abbrev?.status}"
        )
        notificationAlarmGenerator.cancelAlarmsOfLaunch(launch.id)
    }

    private fun hasAlarmTimeChanged(
        launchTime: Long,
        launchTimeNew: Long,
        margin: Long
    ): Boolean {
        if (launchTimeNew > launchTime + margin ||
            launchTimeNew < launchTime - margin
        ) {
            return true
        }
        return false
    }

    private suspend fun sendNotification(
        launch: Launch,
        notificationLevel: NotificationLevel,
        text: String? = null
    ) {
        val notifyTime = launch.net.toEpochMilliSecond() - (notificationLevel.time * 60000L)
        val notificationText = text
            ?: when (notificationLevel) {
                NotificationLevel.DEFAULT -> notificationLevel.description
                else -> "launch in ${notificationLevel.description}"
            }
        logger.v(TAG, "launch time: ${launch.net.toEpochMilliSecond()}notifyTime $notifyTime")
        withContext(dispatcherProvider.getDefaultContext()) {
            val delayAmount = maxOf(notifyTime - timeProviderMillis(), 0)
            delay(delayAmount)
        }
        withContext(dispatcherProvider.getMainContext()) {
            notificationSender.sendNotification(
                LaunchNotification(
                    launch.name.ifEmptyNull() ?: "Unknown Launch",
                    R.mipmap.ic_launcher_round,
                    notificationText,
                    notifyTime
                )
            )
        }
    }
}