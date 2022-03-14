package com.thewire.wenlaunch.cache.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.cache.model.LocationEntity
import com.thewire.wenlaunch.cache.model.PadEntity
import com.thewire.wenlaunch.cache.model.mapToEntity
import com.thewire.wenlaunch.domain.model.Pad

data class PadRelationship(
    @Embedded
    val pad: PadEntity,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "id"
    )
    val location: LocationEntity?

) : IRepoToDomain<Pad> {
    override fun mapToDomainModel(): Pad {
        return Pad(
            id = this.pad.id,
            name = this.pad.name,
            location = this.location?.mapToDomainModel()
        )
    }
}

fun Pad.mapToEntity(): PadRelationship {
    return PadRelationship(
        pad = PadEntity(
            id = this.id ?: throw(IllegalArgumentException("primary key null")),
            name = this.name,
            location = this.location?.id,
            modifiedAt = System.currentTimeMillis()
        ),
        location = this.location?.mapToEntity()
    )
}

