package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.network.LaunchService
import com.thewire.wenlaunch.network.model.mappers.LaunchDtoMapper

class LaunchRepository_Impl (
    private val launchService: LaunchService,
    private val mapper: LaunchDtoMapper
) : LaunchRepository {
    override suspend fun upcoming(limit: Int): List<Launch> {
        return mapper.toDomainList(launchService.upcoming(limit).launches)
    }

    override suspend fun launch(id: String): Launch {
        return mapper.mapToDomainModel(launchService.launch(id))
    }
}