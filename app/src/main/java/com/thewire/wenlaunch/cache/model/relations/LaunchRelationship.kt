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
        val instant = Instant.ofEpochSecond(this.launch.net)
        return Launch(
            id = this.launch.id,
            url = this.launch.url?.let { url -> Uri.parse(url) },
            name = this.launch.name,
            status = this.status?.mapToDomainModel(),
            net = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC),
            rocket = this.rocket?.mapToDomainModel(),
            mission = this.mission?.mapToDomainModel(),
            pad = this.pad?.mapToDomainModel(),
            image = this.launch.image?.let { url -> Uri.parse(url) },
            vidUris = this.launch.vidUrls.map { VidUri(Uri.parse(it)) },
            webcastLive = this.launch.webcastLive ?: false,
            modifiedAt = this.launch.modifiedAt
        )
    }
}

fun Launch.mapToEntity(): LaunchRelationship {
    return LaunchRelationship(
        launch = LaunchEntity(
            id = this.id ?: throw(IllegalArgumentException("primary key null")),
            url = this.url?.toString(),
            name = this.name,
            status = this.status?.id,
            net = this.net.toEpochSecond(),
            rocket = this.rocket?.id,
            mission = this.mission?.id,
            pad = this.pad?.id,
            image = this.image?.toString(),
            webcastLive = this.webcastLive,
            vidUrls = this.vidUris.map { uri -> uri.toString() },
            modifiedAt = System.currentTimeMillis(),
        ),
        status = this.status?.mapToEntity(),
        rocket = this.rocket?.mapToEntity(),
        mission = this.mission?.mapToEntity(),
        pad = this.pad?.mapToEntity(),
    )
}
