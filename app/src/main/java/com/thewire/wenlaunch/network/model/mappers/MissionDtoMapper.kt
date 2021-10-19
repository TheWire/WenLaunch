package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Mission
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.MissionDto
import com.thewire.wenlaunch.network.model.OrbitDto


class MissionDtoMapper : DomainMapper<MissionDto, Mission> {
    private val orbitMapper = OrbitDtoMapper()
    override fun mapToDomainModel(model: MissionDto): Mission {
        return Mission(
            id = model.id,
            name = model.name,
            description = model.description,
            orbit = orbitMapper.mapToDomainModel(model.orbit ?: OrbitDto()),
        )
    }
}