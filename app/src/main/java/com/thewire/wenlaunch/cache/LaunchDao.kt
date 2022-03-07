package com.thewire.wenlaunch.cache

import androidx.room.Dao
import androidx.room.Insert
import com.thewire.wenlaunch.cache.model.LaunchEntity

@Dao
interface LaunchDao {
    @Insert
    suspend fun insertLaunch(launch: LaunchEntity): Long
}