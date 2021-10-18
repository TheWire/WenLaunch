package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Orbit
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.OrbitDto

class OrbitDtoMapper : DomainMapper<OrbitDto, Orbit> {
    override fun mapToDomainModel(model: OrbitDto): Orbit {
        return Orbit(
            id = model.id,
            name = model.name,
            abbrev = model.abbrev
        )
    }
}