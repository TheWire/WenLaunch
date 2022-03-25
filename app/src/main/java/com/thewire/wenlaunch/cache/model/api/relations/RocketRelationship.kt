package com.thewire.wenlaunch.cache.model.api.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.cache.model.api.RocketConfigurationEntity
import com.thewire.wenlaunch.cache.model.api.RocketEntity
import com.thewire.wenlaunch.cache.model.api.mapToEntity
import com.thewire.wenlaunch.domain.model.Rocket

data class RocketRelationship(
    @Embedded
    val rocket: RocketEntity,
    @Relation(
        parentColumn = "configuration_id",
        entityColumn = "id"
    )
    val configuration: RocketConfigurationEntity?
) : IRepoToDomain<Rocket> {
    override fun mapToDomainModel(): Rocket {
        return Rocket(
            id = this.rocket.id,
            configuration = this.configuration?.mapToDomainModel()
        )
    }
}

fun Rocket.mapToEntity(): RocketRelationship {
    return RocketRelationship(
        rocket = RocketEntity(
            id = this.id ?: throw(IllegalArgumentException("primary key null")),
            configuration = this.configuration?.id,
            modifiedAt = System.currentTimeMillis()
        ),
        configuration = this.configuration?.mapToEntity()
    )
}