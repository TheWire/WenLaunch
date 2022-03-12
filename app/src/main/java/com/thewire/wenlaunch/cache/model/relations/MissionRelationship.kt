package com.thewire.wenlaunch.cache.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.cache.model.MissionEntity
import com.thewire.wenlaunch.cache.model.OrbitEntity
import com.thewire.wenlaunch.cache.model.mapToEntity
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
            name = this.name,
            description = this.description,
            modifiedAt = System.currentTimeMillis(),
        ),
        orbit = this.orbit?.mapToEntity()
    )
}
