package com.thewire.wenlaunch.cache.Logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.Logging.model.LogEntry
import com.thewire.wenlaunch.cache.model.IRepoToDomain

@Entity(tableName = "log")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "log_id")
    val logId: Int = 0,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "level")
    val level: String,
    @ColumnInfo(name = "tag")
    val tag: String
) : IRepoToDomain<LogEntry> {
    override fun mapToDomainModel(): LogEntry {
        return LogEntry(
            logId = this.logId,
            message = this.message,
            timestamp = this.timestamp,
            level = this.level,
            tag = this.tag,
        )
    }
}

fun LogEntry.mapToEntity(): LogEntity {
    return LogEntity(
        logId = this.logId,
        message = this.message,
        timestamp = this.timestamp,
        level = this.level,
        tag = this.tag
    )
}
