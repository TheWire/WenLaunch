package com.thewire.wenlaunch.repository.store

import androidx.datastore.core.DataStore
import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.lang.Exception

class SettingsStore_Impl(
    private val dataStore: DataStore<Settings>
) : SettingsStore {
    private val settingsMapper = SettingsMapper()
    override suspend fun getSettings(): SettingsStoreResult =
        withContext(Dispatchers.IO) {
            try {
                val settings = dataStore.data.first()
                SettingsStoreResult.OnSuccess(settingsMapper.mapToDomainModel(settings))
            } catch(e: Exception) {
                SettingsStoreResult.OnError(e)
            }
        }

    override suspend fun setSettings(settingsModel: SettingsModel): SettingsStoreResult =
        withContext(Dispatchers.IO) {
            try {
                dataStore.updateData {
                    it.toBuilder().setDarkMode(settingsModel.darkMode).build()
                }
                SettingsStoreResult.OnComplete
            } catch (e: Exception) {
                SettingsStoreResult.OnError(e)
            }
        }
}