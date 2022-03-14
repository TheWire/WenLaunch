package com.thewire.wenlaunch.cache.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.domain.model.Location

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "map_image")
    val mapImage: String?,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
) : IRepoToDomain<Location> {
    override fun mapToDomainModel(): Location {
        return Location(
            id = this.id,
            name = this.name,
            mapImage = Uri.parse(this.mapImage)
        )
    }
}

fun Location.mapToEntity(): LocationEntity {
    return LocationEntity(
        id = this.id ?: throw(IllegalArgumentException("primary key null")),
        name = this.name,
        mapImage = this.mapImage?.toString(),
        modifiedAt = System.currentTimeMillis()
    )
}
