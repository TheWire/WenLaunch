package com.thewire.wenlaunch.di

import android.content.Context
import androidx.work.DelegatingWorkerFactory
import com.thewire.wenlaunch.notifications.*
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
        repositoryI: ILaunchRepository,
        dispatcher: IDispatcherProvider,
        alarmGenerator: INotificationAlarmGenerator,
    ): DelegatingWorkerFactory {
        val delegatingFactory = DelegatingWorkerFactory()
        delegatingFactory.addFactory(
            NotificationWorkerFactory(repositoryI, dispatcher, alarmGenerator)
        )
        return delegatingFactory
    }

    @Singleton
    @Provides
    fun provideINotificationAlarmGenerator(@ApplicationContext app: Context): INotificationAlarmGenerator {
        return NotificationAlarmGenerator(app)
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
        notificationSender: INotificationSender
    ): NotificationHandler {
        return NotificationHandler(
            repository,
            dispatcher,
            notificationAlarmGenerator,
            notificationSender,
        )
    }
}