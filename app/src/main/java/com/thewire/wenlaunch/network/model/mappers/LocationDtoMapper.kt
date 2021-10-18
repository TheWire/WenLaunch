package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Location
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.LocationDto

class LocationDtoMapper : DomainMapper<LocationDto, Location> {

    override fun mapToDomainModel(model: LocationDto): Location {
        return Location(
            id = model.id,
            name = model.name,
            )
    }
}
