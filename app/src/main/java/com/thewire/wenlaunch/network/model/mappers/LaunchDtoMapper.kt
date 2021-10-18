package com.thewire.wenlaunch.network.model.mappers

import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.LaunchDto
import com.thewire.wenlaunch.network.model.MissionDto
import com.thewire.wenlaunch.network.model.PadDto
import com.thewire.wenlaunch.network.model.RocketDto
import java.net.URL

class LaunchDtoMapper : DomainMapper<LaunchDto, Launch> {
    private val rocketMapper = RocketDtoMapper()
    private val missionMapper = MissionDtoMapper()
    private val padMapper = PadDtoMapper()
    override fun mapToDomainModel(model: LaunchDto): Launch {
        return Launch(
            id = model.id,
            url = URL(model.url),
            name = model.name,
            rocket = rocketMapper.mapToDomainModel(model.rocket ?: RocketDto()),
            mission = missionMapper.mapToDomainModel(model.mission ?: MissionDto()),
            pad = padMapper.mapToDomainModel(model.pad ?: PadDto()),
            image = model.image
        )
    }
}