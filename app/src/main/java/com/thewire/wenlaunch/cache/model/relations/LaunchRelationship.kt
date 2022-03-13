package com.thewire.wenlaunch.cache.model.relations

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.model.*
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.VidUri
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
        val instant = Instant.ofEpochSecond(launch.net)
        return Launch(
            id = launch.id,
            url = launch.url?.let { url -> Uri.parse(url) },
            name = launch.name,
            status = status?.mapToDomainModel(),
            net = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC),
            rocket = rocket?.mapToDomainModel(),
            mission = mission?.mapToDomainModel(),
            vidUris = launch.vidUrls.map { VidUri(Uri.parse(it)) },
            webcastLive = launch.webcastLive ?: false,
            modifiedAt = launch.modifiedAt
        )
    }
}

fun Launch.mapToEntity(): LaunchRelationship {
    return LaunchRelationship(
        launch = LaunchEntity(
            url = this.url?.toString(),
            name = this.name,
            net = this.net.toEpochSecond(),
            image = this.image?.toString(),
            webcastLive = this.webcastLive,
            vidUrls = vidUris.map { uri -> uri.toString() },
            modifiedAt = System.currentTimeMillis(),
        ),
        status = this.status?.mapToEntity(),
        rocket = this.rocket?.mapToEntity(),
        mission = this.mission?.mapToEntity(),
        pad = this.pad?.mapToEntity(),
    )
}
