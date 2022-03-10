package com.thewire.wenlaunch.network.model.mappers

import android.net.Uri
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.VidUri
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.*
import java.time.ZonedDateTime


class LaunchDtoMapper : DomainMapper<LaunchDto, Launch> {
    private val rocketMapper = RocketDtoMapper()
    private val missionMapper = MissionDtoMapper()
    private val padMapper = PadDtoMapper()
    private val statusMapper = StatusDtoMapper()
    private val vidUrlDtoMapper = VidUrlDtoMapper()
    override fun mapToDomainModel(model: LaunchDto): Launch {
        return Launch(
            id = model.id ?: "",
            url =  model.url?.let{ Uri.parse(model.url) },
            name = model.name ?: "",
            status = statusMapper.mapToDomainModel(model.status ?: StatusDto()),
            net = getTimeObject(model.net),
            rocket = rocketMapper.mapToDomainModel(model.rocket ?: RocketDto()),
            mission = missionMapper.mapToDomainModel(model.mission ?: MissionDto()),
            pad = padMapper.mapToDomainModel(model.pad ?: PadDto()),
            image = model.image?.let{ Uri.parse(model.image) },
            webcastLive = model.webcastLive ?: false,
            vidUris = vidUrlDtoMapper.toDomainList(model.vidURLs ?: listOf())
        )
    }

    fun toDomainList(initial: List<LaunchDto>) : List<Launch> {
        return initial.map {
            mapToDomainModel(it)
        }
    }
}