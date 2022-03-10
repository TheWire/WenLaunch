package com.thewire.wenlaunch.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.thewire.wenlaunch.cache.model.*
import com.thewire.wenlaunch.cache.model.relations.LaunchRelationship
import com.thewire.wenlaunch.cache.model.relations.MissionRelationship
import com.thewire.wenlaunch.cache.model.relations.PadRelationship
import com.thewire.wenlaunch.cache.model.relations.RocketRelationship

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchEntity(launchEntity: LaunchEntity): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(statusEntity: StatusEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPadEntity(padEntity: PadEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissionEntity(missionEntity: MissionEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrbit(orbitEntity: OrbitEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocketEntity(rocketEntity: RocketEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocketConfiguration(rocketConfigurationEntity: RocketConfigurationEntity): Int


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: RocketRelationship): Int {
        rocket.configuration?.let { configuration ->
            insertRocketConfiguration(configuration)
        }
        return insertRocketEntity(rocket.rocket)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPad(pad: PadRelationship): Int {
        pad.location?.let { location ->
            insertLocation(location)
        }
        return insertPadEntity(pad.pad)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: MissionRelationship): Int {
        mission.orbit?.let { orbit ->
            insertOrbit(orbit)
        }
        return insertMissionEntity(mission.mission)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: LaunchRelationship): String {
        launch.status?.let { status ->
            insertStatus(status)
        }
        launch.mission?.let { mission ->
            insertMission(mission)
        }
        launch.rocket?.let { rocket ->
            insertRocket(rocket)
        }
        launch.pad?.let { pad ->
            insertPad(pad)
        }
        return insertLaunchEntity(launch.launch)
    }
}