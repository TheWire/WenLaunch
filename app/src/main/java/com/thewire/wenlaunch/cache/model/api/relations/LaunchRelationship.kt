package com.thewire.wenlaunch.cache.model.api.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.cache.model.api.*
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.VidUrl
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

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

    ) : IRepoToDomain<Launch> {
    override fun mapToDomainModel(): Launch {
        val instant = Instant.ofEpochSecond(this.launch.net)
        return Launch(
            id = this.launch.id,
            url = this.launch.url,
            name = this.launch.name,
            status = this.status?.mapToDomainModel(),
            net = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC),
            rocket = this.rocket?.mapToDomainModel(),
            mission = this.mission?.mapToDomainModel(),
            pad = this.pad?.mapToDomainModel(),
            image = this.launch.image,
            vidUrls = this.launch.vidUrls.map { VidUrl(it) },
            webcastLive = this.launch.webcastLive ?: false,
            modifiedAt = this.launch.modifiedAt
        )
    }
}

fun Launch.mapToEntity(): LaunchRelationship {
    return LaunchRelationship(
        launch = LaunchEntity(
            id = this.id,
            url = this.url,
            name = this.name,
            status = this.status?.id,
            net = this.net.toEpochSecond(),
            rocket = this.rocket?.id,
            mission = this.mission?.id,
            pad = this.pad?.id,
            image = this.image,
            webcastLive = this.webcastLive,
            vidUrls = this.vidUrls.map { uri -> uri.toString() },
            modifiedAt = System.currentTimeMillis(),
        ),
        status = this.status?.mapToEntity(),
        rocket = this.rocket?.mapToEntity(),
        mission = this.mission?.mapToEntity(),
        pad = this.pad?.mapToEntity(),
    )
}
