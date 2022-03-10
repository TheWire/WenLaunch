package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.LaunchStatus.*
import com.thewire.wenlaunch.domain.model.Status
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.StatusDto

class StatusDtoMapper : DomainMapper<StatusDto, Status> {

    override fun mapToDomainModel(model: StatusDto): Status {

        return Status(
            id = model.id,
            name = model.name ?: "",
            abbrev = LaunchStatus.getLaunchStatus(model.abbrev),
            description = model.description ?: "",
        )
    }

}