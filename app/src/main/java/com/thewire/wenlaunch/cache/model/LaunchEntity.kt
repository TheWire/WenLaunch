package com.thewire.wenlaunch.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class LaunchEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "status_id")
    val status: Int?,
    @ColumnInfo(name = "net")
    val net: String,
    @ColumnInfo(name = "rocket_id")
    val rocket: Int?,
    @ColumnInfo(name = "mission_id")
    val mission: Int?,
    @ColumnInfo(name = "pad_id")
    val pad: Int?,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "webcast_live")
    val webcastLive: Boolean?,
    @ColumnInfo(name = "video_Urls")
    val vidUrls: List<String>,
    @ColumnInfo(name = "modified_at")
    val ModifiedAt: Long
)
