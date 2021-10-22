package com.thewire.wenlaunch.repository.store

import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import java.lang.Exception

interface SettingsStore {
    suspend fun getSettings(): SettingsStoreResult
    suspend fun setSettings(settingsModel: SettingsModel): SettingsStoreResult
}

sealed class SettingsStoreResult {
    data class OnSuccess(val settings: SettingsModel) : SettingsStoreResult()
    data class OnError(val exception: Exception) : SettingsStoreResult()
    object OnComplete : SettingsStoreResult()
}