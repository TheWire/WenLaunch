package com.thewire.wenlaunch.cache.alarm.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.thewire.wenlaunch.cache.alarm.AlarmEntity
import com.thewire.wenlaunch.cache.model.api.LaunchEntity

data class AlarmRelationship(
    @Embedded
    val Alarm: AlarmEntity,
    @Relation(
        entity = LaunchEntity::class,
        parentColumn = "launch_id",
        entityColumn = "id"
    )
    val launch: LaunchEntity
)
