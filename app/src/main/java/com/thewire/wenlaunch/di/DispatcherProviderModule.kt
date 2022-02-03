package com.thewire.wenlaunch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherProviderModule {
    @Singleton
    @Provides
    fun provideDispatcherProvider(): IDispatcherProvider {
        return ProductionDispatcherProviderImpl
    }
}