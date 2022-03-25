package com.thewire.wenlaunch.notifications.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.repository.ILaunchRepository
import javax.inject.Inject

class NotificationWorkerFactory
@Inject
constructor(
    private val repository: ILaunchRepository,
    private val dispatcher: IDispatcherProvider,
    private val notificationAlarmGenerator: INotificationAlarmGenerator
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            NotificationWorker::class.java.name ->
                NotificationWorker(
                    appContext,
                    workerParameters,
                    repository, dispatcher,
                    notificationAlarmGenerator
                )
            else -> null
        }
    }
}