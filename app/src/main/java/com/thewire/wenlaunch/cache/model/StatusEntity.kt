package com.thewire.wenlaunch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
)
