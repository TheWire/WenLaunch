package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Rocket
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.RocketConfigurationDto
import com.thewire.wenlaunch.network.model.RocketDto

class RocketDtoMapper : DomainMapper<RocketDto, Rocket> {
    private val rocketConfigurationDtoMapper = RocketConfigurationDtoMapper()
    override fun mapToDomainModel(model: RocketDto): Rocket {
        return Rocket(
            id = model.id,
            configuration = rocketConfigurationDtoMapper.mapToDomainModel(model.configuration ?: RocketConfigurationDto()),
        )
    }
}
