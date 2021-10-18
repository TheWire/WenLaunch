package com.thewire.wenlaunch.network.model.mappers

import android.net.Uri
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.LaunchDto
import com.thewire.wenlaunch.network.model.MissionDto
import com.thewire.wenlaunch.network.model.PadDto
import com.thewire.wenlaunch.network.model.RocketDto


class LaunchDtoMapper : DomainMapper<LaunchDto, Launch> {
    private val rocketMapper = RocketDtoMapper()
    private val missionMapper = MissionDtoMapper()
    private val padMapper = PadDtoMapper()
    override fun mapToDomainModel(model: LaunchDto): Launch {
        return Launch(
            id = model.id,
            url =  Uri.parse(model.url),
            name = model.name,
            rocket = rocketMapper.mapToDomainModel(model.rocket ?: RocketDto()),
            mission = missionMapper.mapToDomainModel(model.mission ?: MissionDto()),
            pad = padMapper.mapToDomainModel(model.pad ?: PadDto()),
            image = Uri.parse(model.image)
        )
    }

    fun toDomainList(initial: List<LaunchDto>) : List<Launch> {
        return initial.map {
            mapToDomainModel(it)
        }
    }
}