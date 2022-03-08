package com.thewire.wenlaunch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket_configuration")
data class RocketConfigurationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "family")
    val family: String,
    @ColumnInfo(name = "variant")
    val variant: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
)
