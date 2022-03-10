package com.thewire.wenlaunch.cache.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IEntityToDomain
import com.thewire.wenlaunch.cache.model.LocationEntity
import com.thewire.wenlaunch.cache.model.PadEntity
import com.thewire.wenlaunch.domain.model.*

data class PadRelationship(
    @Embedded
    val pad: PadEntity,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "id"
    )
    val location: LocationEntity?

    ) : IEntityToDomain<Pad> {
    override fun mapToDomain(): Pad {
        return Pad(
            id = this.pad.id,
            name = this.pad.name,
            location = this.location?.mapToDomain()
        )
    }
}

