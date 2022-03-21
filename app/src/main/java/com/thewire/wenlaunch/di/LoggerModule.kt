package com.thewire.wenlaunch.di

import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.Logging.LoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
    @Singleton
    @Provides
    fun provideLogger(): ILogger {
        return LoggerImpl()
    }
}