package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.RocketConfiguration
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.RocketConfigurationDto

class RocketConfigurationDtoMapper : DomainMapper<RocketConfigurationDto, RocketConfiguration> {
    private val rocketMapper = RocketDtoMapper()
    override fun mapToDomainModel(model: RocketConfigurationDto): RocketConfiguration {
        return RocketConfiguration(
            id = model.id,
            name = model.name
        )
    }
}
