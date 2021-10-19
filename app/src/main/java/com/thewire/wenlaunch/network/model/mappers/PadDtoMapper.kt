package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Pad
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.LocationDto
import com.thewire.wenlaunch.network.model.PadDto

class PadDtoMapper : DomainMapper<PadDto, Pad> {
    private val locationMapper = LocationDtoMapper()

    override fun mapToDomainModel(model: PadDto): Pad {
        return Pad(
            id = model.id,
            name = model.name,
            location = locationMapper.mapToDomainModel(model.location ?: LocationDto()),

        )
    }
}
