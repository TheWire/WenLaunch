package com.thewire.wenlaunch.di

import com.thewire.wenlaunch.BuildConfig
import com.thewire.wenlaunch.Logging.ILogger
import com.thewire.wenlaunch.Logging.LoggerImpl
import com.thewire.wenlaunch.repository.ILaunchRepository
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
    fun provideLogger(
        repository: ILaunchRepository,
        dispatcher: IDispatcherProvider,
    ) : ILogger {
        return LoggerImpl(
            logToDatabase = BuildConfig.LOG_TO_DATABASE,
            repository = repository,
            dispatcher = dispatcher
        )
    }
}