package com.thewire.wenlaunch.cache.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IEntityToDomain
import com.thewire.wenlaunch.cache.model.MissionEntity
import com.thewire.wenlaunch.cache.model.OrbitEntity
import com.thewire.wenlaunch.domain.model.Mission

data class MissionRelationship(
    @Embedded
    val mission: MissionEntity,
    @Relation(
        parentColumn = "orbit_id",
        entityColumn = "id"
    )
    val orbit: OrbitEntity?
): IEntityToDomain<Mission> {
    override fun mapToDomain(): Mission {
        return Mission(
            id = this.mission.id,
            name = this.mission.name,
            description = this.mission.description,
            orbit = this.orbit?.mapToDomain()
        )
    }
}

