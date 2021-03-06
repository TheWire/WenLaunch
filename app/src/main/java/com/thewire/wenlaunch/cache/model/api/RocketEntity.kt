package com.thewire.wenlaunch.cache.model.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket")
data class RocketEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "configuration_id")
    val configuration: Int? = null,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
)

