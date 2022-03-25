package com.thewire.wenlaunch.cache.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thewire.wenlaunch.cache.model.IRepoToDomain
import com.thewire.wenlaunch.notifications.model.Alarm

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "request_id")
    val requestId: Int,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "launch_id")
    val launchId: String
) : IRepoToDomain<Alarm> {
    override fun mapToDomainModel(): Alarm {
        return Alarm(
            requestId = this.requestId,
            time = this.time,
            launchId = this.launchId
        )
    }
}

fun Alarm.mapToEntity(): AlarmEntity {
    return AlarmEntity(
        requestId = this.requestId,
        time = this.time,
        launchId = this.launchId
    )
}
