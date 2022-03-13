package com.thewire.wenlaunch.di

import androidx.room.Room
import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.cache.database.LaunchDatabase
import com.thewire.wenlaunch.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDb(app: BaseApplication): LaunchDatabase {
        return Room
            .databaseBuilder(app, LaunchDatabase::class.java, LaunchDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesLaunchDao(database: LaunchDatabase): LaunchDao {
        return database.launchDao()
    }
}