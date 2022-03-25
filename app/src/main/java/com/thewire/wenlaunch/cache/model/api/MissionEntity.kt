package com.thewire.wenlaunch.cache.model.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission")
data class MissionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "orbit_id")
    val orbit: Int? = null,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
)
