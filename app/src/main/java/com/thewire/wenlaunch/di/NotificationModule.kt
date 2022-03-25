package com.thewire.wenlaunch.di

import android.content.Context
import androidx.work.DelegatingWorkerFactory
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.notifications.*
import com.thewire.wenlaunch.notifications.alarm.INotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.alarm.NotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.workers.NotificationWorkerFactory
import com.thewire.wenlaunch.repository.ILaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun providesNotificationWorkerFactory(
        repository: ILaunchRepository,
        dispatcher: IDispatcherProvider,
        alarmGenerator: INotificationAlarmGenerator,
    ): DelegatingWorkerFactory {
        val delegatingFactory = DelegatingWorkerFactory()
        delegatingFactory.addFactory(
            NotificationWorkerFactory(repository, dispatcher, alarmGenerator)
        )
        return delegatingFactory
    }

    @Singleton
    @Provides
    fun provideINotificationAlarmGenerator(@ApplicationContext app: Context, repository: ILaunchRepository): INotificationAlarmGenerator {
        return NotificationAlarmGenerator(app, repository)
    }

    @Singleton
    @Provides
    fun provideINotificationSender(
        @ApplicationContext app: Context,
    ): INotificationSender {
        return NotificationSender(app)
    }

    @Singleton
    @Provides
    fun provideNotificationHandler(
        repository: ILaunchRepository,
        dispatcher: IDispatcherProvider,
        notificationAlarmGenerator: INotificationAlarmGenerator,
        notificationSender: INotificationSender,
        logger: ILogger
    ): NotificationHandler {
        return NotificationHandler(
            repository,
            dispatcher,
            notificationAlarmGenerator,
            notificationSender,
            logger,
            System::currentTimeMillis,
        )
    }
}