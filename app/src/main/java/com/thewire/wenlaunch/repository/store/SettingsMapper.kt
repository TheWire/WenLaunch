package com.thewire.wenlaunch.repository.store

import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.SettingsOrBuilder
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.domain.util.DomainMapper

class SettingsMapper : DomainMapper<Settings, SettingsModel> {
    override fun mapToDomainModel(model: Settings): SettingsModel {
        return SettingsModel(
            darkMode = model.darkMode
        )
    }
}