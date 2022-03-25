package com.thewire.wenlaunch.cache.model.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.domain.model.Orbit

@Entity(tableName = "orbit")
data class OrbitEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "abbrev")
    val abbrev: String,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
) : IRepoToDomain<Orbit> {
    override fun mapToDomainModel(): Orbit {
        return Orbit(
            id = this.id,
            name = this.name,
            abbrev = this.abbrev
        )
    }
}

fun Orbit.mapToEntity(): OrbitEntity {
    return OrbitEntity(
        id = this.id ?: throw(IllegalArgumentException("primary key null")),
        name = this.name,
        abbrev = this.abbrev,
        modifiedAt = System.currentTimeMillis(),
    )
}
