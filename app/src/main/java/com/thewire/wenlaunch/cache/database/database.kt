package com.thewire.wenlaunch.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.cache.Logging.LogEntity
import com.thewire.wenlaunch.cache.alarm.AlarmEntity
import com.thewire.wenlaunch.cache.model.api.*
import com.thewire.wenlaunch.cache.model.typeConverters.LaunchTypeConverter


@Database(
    entities = [
        LaunchEntity::class,
        StatusEntity::class,
        RocketEntity::class,
        RocketConfigurationEntity::class,
        MissionEntity::class,
        OrbitEntity::class,
        PadEntity::class,
        LocationEntity::class,
        AlarmEntity::class,
        LogEntity::class
    ], version = 1
)
@TypeConverters(LaunchTypeConverter::class)
abstract class LaunchDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao

    companion object {
        const val DATABASE_NAME = "launch_db"
    }
}