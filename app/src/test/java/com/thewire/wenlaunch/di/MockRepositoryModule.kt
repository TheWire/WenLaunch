package com.thewire.wenlaunch.di

import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.repository.MockRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MockRepositoryModule {

    @Singleton
    @Provides
    fun provideLaunchRepository(
    ) : ILaunchRepository {
        return MockRepositoryImpl()
    }


}