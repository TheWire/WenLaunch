package com.thewire.wenlaunch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.domain.model.Status

@Entity(tableName = "status")
data class StatusEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "abbrev")
    val abbrev: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
) : IRepoToDomain<Status> {
    override fun mapToDomainModel(): Status {
        return Status(
            id = this.id,
            name = this.name,
            abbrev = LaunchStatus.getLaunchStatus(this.abbrev),
            description = this.description
        )
    }
}

fun Status.mapToEntity(): StatusEntity {
    return StatusEntity(
        name = this.name,
        abbrev = this.abbrev.status,
        description = this.description,
        modifiedAt = System.currentTimeMillis(),
    )
}
