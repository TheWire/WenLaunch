package com.thewire.wenlaunch.di

import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.network.LaunchService
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLaunchRepository(
        launchService: LaunchService,
        launchDao: LaunchDao
    ) : LaunchRepository {
        return LaunchRepositoryImpl(launchDao, launchService)
    }
}