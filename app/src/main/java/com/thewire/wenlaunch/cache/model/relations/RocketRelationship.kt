package com.thewire.wenlaunch.cache.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IEntityToDomain
import com.thewire.wenlaunch.cache.model.RocketConfigurationEntity
import com.thewire.wenlaunch.cache.model.RocketEntity
import com.thewire.wenlaunch.domain.model.Rocket

data class RocketRelationship (
    @Embedded
    val rocket: RocketEntity,
    @Relation(
        parentColumn = "configuration_id",
        entityColumn = "id"
    )
    val configuration: RocketConfigurationEntity?
) : IEntityToDomain<Rocket> {
    override fun mapToDomain(): Rocket {
        return Rocket(
            id = this.rocket.id,
            configuration = this.configuration?.mapToDomain()
        )
    }
}