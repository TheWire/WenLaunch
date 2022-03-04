package com.thewire.wenlaunch.di

import androidx.datastore.core.DataStore
import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.repository.store.settingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(app: BaseApplication): DataStore<Settings> {
        return app.applicationContext.settingsDataStore
    }
}