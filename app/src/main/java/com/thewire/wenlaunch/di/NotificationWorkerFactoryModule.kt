package com.thewire.wenlaunch.di

import androidx.work.DelegatingWorkerFactory
import com.thewire.wenlaunch.notifications.NotificationAlarmGenerator
import com.thewire.wenlaunch.notifications.NotificationWorkerFactory
import com.thewire.wenlaunch.repository.LaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationWorkerFactoryModule {

    @Singleton
    @Provides
    fun providesNotificationWorkerFactory(
        repository: LaunchRepository,
        dispatcher: IDispatcherProvider,
        alarmGenerator: NotificationAlarmGenerator,
    ): DelegatingWorkerFactory {
        val delegatingFactory = DelegatingWorkerFactory()
        delegatingFactory.addFactory(
            NotificationWorkerFactory(repository, dispatcher, alarmGenerator)
        )
        return delegatingFactory
    }
}