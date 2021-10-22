package com.thewire.wenlaunch.di

import androidx.datastore.core.DataStore
import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.repository.store.SettingsStore
import com.thewire.wenlaunch.repository.store.SettingsStore_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Singleton
    @Provides
    fun provideSettingsStore(
        dataStore: DataStore<Settings>
    ) : SettingsStore {
        return SettingsStore_Impl(dataStore)
    }
}