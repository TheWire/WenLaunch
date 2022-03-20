package com.thewire.wenlaunch.notifications

import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import com.thewire.wenlaunch.util.ifEmptyNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationHandler(
    private var repository: ILaunchRepository,
    private var dispatcherProvider: IDispatcherProvider,
    private var notificationAlarmGenerator: INotificationAlarmGenerator,
    private var notificationSender: INotificationSender,
) {

    fun updateAndNotify(
        id: String,
        launchTime: Long,
        notificationLevel: NotificationLevel,
        notifications: Map<NotificationLevel, Boolean>?
    ) {
        CoroutineScope(dispatcherProvider.getIOContext()).launch {
            repository.launch(
                id,
                LaunchRepositoryUpdatePolicy.NetworkPrimary
            ).collect { dataState ->
                dataState.data?.let { launch ->
                    if (launch.status?.abbrev == LaunchStatus.GO) {
                        val launchTimeNew = launch.net.toEpochSecond()
                        if (launchTimeNew + ALARM_RECEIVER_LAUNCH_MARGIN > launchTime &&
                            launchTimeNew - ALARM_RECEIVER_LAUNCH_MARGIN < launchTime
                        ) {
                            sendNotification(launch, notificationLevel)
                        } else {
                            notificationAlarmGenerator.cancelAlarms(launch.id)
                            notifications?.let {
                                notificationAlarmGenerator.setLaunchAlarms(launch, it)
                            }
                        }
                    } else {
                        notificationAlarmGenerator.cancelAlarms(launch.id)
                    }
                }
            }
        }
    }

    private suspend fun sendNotification(
        launch: Launch,
        notificationLevel: NotificationLevel
    ) {
        val notifyTime = launch.net.toEpochSecond() - (notificationLevel.time * 60L)
        val text = when (notificationLevel) {
            NotificationLevel.DEFAULT,
            NotificationLevel.WEBCAST,
            NotificationLevel.LAUNCH -> notificationLevel.description
            else -> "launch in ${notificationLevel.description}"
        }

        withContext(dispatcherProvider.getDefaultContext()) {
            val delayAmount = maxOf((notifyTime * 1000) - System.currentTimeMillis(), 0)
            delay(delayAmount)
        }
        withContext(dispatcherProvider.getMainContext()) {
            notificationSender.sendNotification(
                launch.name.ifEmptyNull() ?: "Unknown Launch",
                R.mipmap.ic_launcher_round,
                text,
                notifyTime
            )
        }
    }
}