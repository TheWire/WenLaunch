package com.thewire.wenlaunch.di

import android.content.Context
import com.thewire.wenlaunch.notifications.NotificationAlarmGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmGeneratorModule {

    @Singleton
    @Provides
    fun provideAlarmGenerator(@ApplicationContext app: Context): NotificationAlarmGenerator {
        return NotificationAlarmGenerator(app)
    }

}