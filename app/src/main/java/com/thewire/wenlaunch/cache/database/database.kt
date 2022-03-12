package com.thewire.wenlaunch.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.cache.model.LaunchEntity
import com.thewire.wenlaunch.cache.model.typeConverters.LaunchTypeConverter


@Database(entities = [LaunchEntity::class], version = 1)
@TypeConverters(LaunchTypeConverter::class)
abstract class LaunchDatabase :  RoomDatabase(){
    abstract fun launchDao(): LaunchDao
    companion object {
        const val DATABASE_NAME = "launch_db"
    }
}