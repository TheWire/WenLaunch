package com.thewire.wenlaunch.network.model.mappers

import android.net.Uri
import com.thewire.wenlaunch.domain.model.VidUri
import com.thewire.wenlaunch.domain.util.DomainMapper
import com.thewire.wenlaunch.network.model.VidURLDto

class VidUrlDtoMapper : DomainMapper<VidURLDto, VidUri> {
    override fun mapToDomainModel(model: VidURLDto): VidUri {
        return VidUri(
            uri = model.url?.let { Uri.parse(it) }
        )
    }

    fun toDomainList(initial: List<VidURLDto>): List<VidUri> {
        return initial.map {
            mapToDomainModel(it)
        }
    }
}
