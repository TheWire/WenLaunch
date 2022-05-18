package com.thewire.wenlaunch.cache

import androidx.room.*
import com.thewire.wenlaunch.cache.Logging.LogEntity
import com.thewire.wenlaunch.cache.alarm.AlarmEntity
import com.thewire.wenlaunch.cache.model.api.*
import com.thewire.wenlaunch.cache.model.api.relations.LaunchRelationship
import com.thewire.wenlaunch.cache.model.api.relations.MissionRelationship
import com.thewire.wenlaunch.cache.model.api.relations.PadRelationship
import com.thewire.wenlaunch.cache.model.api.relations.RocketRelationship

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunchEntity(launchEntity: LaunchEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(statusEntity: StatusEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationEntity: LocationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPadEntity(padEntity: PadEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissionEntity(missionEntity: MissionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrbit(orbitEntity: OrbitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocketEntity(rocketEntity: RocketEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocketConfiguration(rocketConfigurationEntity: RocketConfigurationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarms(alarms: List<AlarmEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(logEntity: LogEntity): Long


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRocket(rocket: RocketRelationship): Long {
        rocket.configuration?.let { configuration ->
            insertRocketConfiguration(configuration)
        }
        return insertRocketEntity(rocket.rocket)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPad(pad: PadRelationship): Long {
        pad.location?.let { location ->
            insertLocation(location)
        }
        return insertPadEntity(pad.pad)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMission(mission: MissionRelationship): Long {
        mission.orbit?.let { orbit ->
            insertOrbit(orbit)
        }
        return insertMissionEntity(mission.mission)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launch: LaunchRelationship): Long {
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchRelationship>): LongArray {
        val ret = arrayListOf<Long>()
        launches.forEach { launch ->
            ret.add(insertLaunch(launch))
        }
        return ret.toLongArray()
    }

    @Transaction
    @Query("SELECT * FROM launch WHERE id=:launchId")
    suspend fun getLaunch(launchId: String): LaunchRelationship?

    @Transaction
    @Query(
        """
        SELECT * FROM launch
        ORDER BY net ASC LIMIT :limit OFFSET :offset
        """
    )
    suspend fun getUpcoming(limit: Int, offset: Int): List<LaunchRelationship>

    @Query("SELECT * FROM alarm WHERE request_id=:id")
    suspend fun getAlarm(id: Int): AlarmEntity

    @Query("SELECT * FROM alarm")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Query("SELECT * FROM alarm WHERE launch_id=:launchId")
    suspend fun getAlarmsByLaunchId(launchId: String): List<AlarmEntity>

    @Query("DELETE FROM alarm WHERE request_id=:id")
    suspend fun deleteAlarm(id: Int): Int

    @Query("DELETE FROM alarm WHERE launch_id=:launchId")
    suspend fun deleteAlarmByLaunchId(launchId: String): Int

    @Query("DELETE FROM alarm")
    suspend fun deleteAllAlarms(): Int
}