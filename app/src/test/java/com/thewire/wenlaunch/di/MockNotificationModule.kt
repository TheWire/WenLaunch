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
object MockNotificationModule {

//    @Singleton
//    @Provides
//    fun providesNotificationWorkerFactory(
//        repositoryI: ILaunchRepository,
//        dispatcher: IDispatcherProvider,
//        alarmGenerator: INotificationAlarmGenerator,
//    ): DelegatingWorkerFactory {
//        val delegatingFactory = DelegatingWorkerFactory()
//        delegatingFactory.addFactory(
//            NotificationWorkerFactory(repositoryI, dispatcher, alarmGenerator)
//        )
//        return delegatingFactory
//    }

    @Singleton
    @Provides
    fun provideINotificationAlarmGenerator(): INotificationAlarmGenerator {
        return MockNotificationAlarmGenerator()
    }

    @Singleton
    @Provides
    fun provideINotificationSender(
    ): INotificationSender {
        return MockNotificationSender()
    }
}