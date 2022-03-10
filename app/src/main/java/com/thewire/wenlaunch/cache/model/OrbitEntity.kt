package com.thewire.wenlaunch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val ModifiedAt: Long,
) : IEntityToDomain<Orbit> {
    override fun mapToDomain(): Orbit {
        return Orbit(
            id = this.id,
            name = this.name,
            abbrev = this.abbrev
        )
    }
}
