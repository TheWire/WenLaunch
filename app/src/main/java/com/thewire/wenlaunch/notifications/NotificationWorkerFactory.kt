package com.thewire.wenlaunch.notifications

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.repository.LaunchRepository
import javax.inject.Inject

class NotificationWorkerFactory
@Inject
constructor(
    private val repository: LaunchRepository,
    private val dispatcher: IDispatcherProvider
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            NotificationWorker::class.java.name ->
                NotificationWorker(appContext, workerParameters, repository, dispatcher)
            else -> null
        }
    }
}