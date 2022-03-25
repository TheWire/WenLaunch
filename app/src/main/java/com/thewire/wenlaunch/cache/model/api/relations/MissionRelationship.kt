package com.thewire.wenlaunch.cache.model.api.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.cache.model.api.MissionEntity
import com.thewire.wenlaunch.cache.model.api.OrbitEntity
import com.thewire.wenlaunch.cache.model.api.mapToEntity
import com.thewire.wenlaunch.domain.model.Mission

data class MissionRelationship(
    @Embedded
    val mission: MissionEntity,
    @Relation(
        parentColumn = "orbit_id",
        entityColumn = "id"
    )
    val orbit: OrbitEntity?
) : IRepoToDomain<Mission> {
    override fun mapToDomainModel(): Mission {
        return Mission(
            id = this.mission.id,
            name = this.mission.name,
            description = this.mission.description,
            orbit = this.orbit?.mapToDomainModel()
        )
    }
}

fun Mission.mapToEntity(): MissionRelationship {
    return MissionRelationship(
        mission = MissionEntity(
            id = this.id ?: throw(IllegalArgumentException("primary key null")),
            name = this.name,
            description = this.description,
            orbit = this.orbit?.id,
            modifiedAt = System.currentTimeMillis(),
        ),
        orbit = this.orbit?.mapToEntity()
    )
}

