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
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "abbrev")
    val abbrev: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "modified_at")
    val ModifiedAt: Long,
) : IEntityToDomain<Status> {
    override fun mapToDomain(): Status {
        return Status(
            id = this.id,
            name = this.name,
            abbrev = LaunchStatus.getLaunchStatus(this.abbrev),
            description = this.description
        )
    }
}
