package com.thewire.wenlaunch.repository.store

import com.thewire.wenlaunch.Settings
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel.*
import com.thewire.wenlaunch.domain.model.settings.SettingsModel
import com.thewire.wenlaunch.domain.util.DomainMapper

class SettingsMapper : DomainMapper<Settings, SettingsModel> {
    override fun mapToDomainModel(model: Settings): SettingsModel {
        return SettingsModel(
            darkMode = model.darkMode,
            mapOf(
                HOURS24 to model.notification24Hours,
                HOURS1 to model.notification1Hour,
//                WEBCAST to model.notificationWebcast,
                MINUTES10 to model.notification10Minutes,
                MINUTES1 to model.notification1Minute,
//                LAUNCH to model.notificationLaunch,
            )
        )
    }
}