package com.thewire.wenlaunch.cache.model.relations

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.*
import com.thewire.wenlaunch.domain.model.*
import com.thewire.wenlaunch.network.model.mappers.getTimeObject

data class LaunchRelationship(
    @Embedded
    val launch: LaunchEntity,
    @Relation(
        parentColumn = "status_id",
        entityColumn = "id"
    )
    val status: StatusEntity?,
    @Relation(
        entity = RocketEntity::class,
        parentColumn = "rocket_id",
        entityColumn = "id"
    )
    val rocket: RocketRelationship?,
    @Relation(
        entity = MissionEntity::class,
        parentColumn = "mission_id",
        entityColumn = "id"
    )
    val mission: MissionRelationship?,
    @Relation(
        entity = PadEntity::class,
        parentColumn = "pad_id",
        entityColumn = "id"
    )
    val pad: PadRelationship?,

) :IEntityToDomain<Launch> {
    override fun mapToDomain(): Launch {
        return Launch(
            id = this.launch.id,
            name = this.launch.name,
            status = this.status?.mapToDomain(),
            net = getTimeObject(this.launch.net),
            rocket = this.rocket?.mapToDomain(),
            mission = this.mission?.mapToDomain(),
            vidUris = this.launch.vidUrls.map{ VidUri(Uri.parse(it)) },
            webcastLive = this.launch.webcastLive ?: false
        )
    }
}
