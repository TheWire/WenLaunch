package com.thewire.wenlaunch.cache.model.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pad")
data class PadEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "location_id")
    val location: Int? = null,
    @ColumnInfo(name = "modified_at")
    val modifiedAt: Long,
)
