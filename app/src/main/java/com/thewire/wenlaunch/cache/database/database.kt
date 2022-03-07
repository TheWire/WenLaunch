package com.thewire.wenlaunch.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thewire.wenlaunch.cache.model.LaunchEntity


@Database(entities = [LaunchEntity::class], version = 1)
abstract class LaunchDatabase :  RoomDatabase(){
    companion object {
        val DATABASE_NAME = "launch_db"
    }
}