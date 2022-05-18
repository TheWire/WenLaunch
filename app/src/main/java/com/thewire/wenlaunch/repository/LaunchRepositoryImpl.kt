package com.thewire.wenlaunch.repository

import android.util.Log
import com.thewire.wenlaunch.Logging.model.LogEntry
import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.cache.Logging.mapToEntity
import com.thewire.wenlaunch.cache.alarm.mapToEntity
import com.thewire.wenlaunch.cache.model.api.relations.mapToEntity
import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.network.LaunchService
import com.thewire.wenlaunch.notifications.model.Alarm
import com.thewire.wenlaunch.presentation.components.TAG
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException

class LaunchRepositoryImpl(
    private val launchDao: LaunchDao,
    private val launchService: LaunchService,
) : ILaunchRepository {

    override fun launch(id: String, updatePolicy: LaunchRepositoryUpdatePolicy):
            Flow<DataState<Launch?>> {
        return getWithUpdatePolicy(
            updatePolicy = updatePolicy,
            getFromCache = suspend { getLaunchFromCache(id) },
            getFromRemoteAndUpdateCache = suspend {
                getLaunchRemoteAndUpdateCache(id)
            },
        ) { launch, threshold -> launch == null || launch.modifiedAt < threshold }
    }

    override fun upcoming(
        limit: Int,
        offset: Int,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<List<Launch>>> {
        return getWithUpdatePolicy(
            updatePolicy = updatePolicy,
            getFromCache = suspend { getUpcomingFromCache(limit, offset) },
            getFromRemoteAndUpdateCache = suspend {
                getUpcomingRemoteAndUpdateCache(
                    limit,
                    offset
                )
            },
        ) { upcomingLaunches, threshold ->
            upcomingLaunches.isNullOrEmpty() || upcomingLaunches[0].modifiedAt < threshold
        }
    }

    override fun alarm(requestId: Int): Flow<DataState<Alarm>> = flow {
        emit(DataState.loading())
        try {
            val alarm = launchDao.getAlarm(requestId).mapToDomainModel()
            emit(DataState.success(alarm))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun alarmsAll(): Flow<DataState<List<Alarm>>> = flow {
        emit(DataState.loading())
        try {
            val alarms = launchDao.getAllAlarms().map { alarm -> alarm.mapToDomainModel() }
            emit(DataState.success(alarms))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun alarmsOfLaunch(launchId: String): Flow<DataState<List<Alarm>>> = flow {
        emit(DataState.loading())
        try {
            val alarms =
                launchDao.getAlarmsByLaunchId(launchId).map { alarm -> alarm.mapToDomainModel() }
            emit(DataState.success(alarms))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun deleteAllAlarms(): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        try {
            val alarms =
                launchDao.deleteAllAlarms()
            emit(DataState.success(alarms))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun deleteAlarm(requestId: Int): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        try {
            val alarms =
                launchDao.deleteAlarm(requestId)
            emit(DataState.success(alarms))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun deleteAlarmsOfLaunch(launchId: String): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        try {
            val alarms =
                launchDao.deleteAlarmByLaunchId(launchId)
            emit(DataState.success(alarms))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    private fun <T> getWithUpdatePolicy(
        updatePolicy: LaunchRepositoryUpdatePolicy,
        getFromCache: suspend () -> T,
        getFromRemoteAndUpdateCache: suspend () -> T,
        meetsThreshold: (T?, threshold: Long) -> Boolean
    ): Flow<DataState<T>> = flow {
        var cacheEmitted = false
        var cache: T? = null
        try {
            emit(DataState.loading())
            cache = if (updatePolicy != NetworkOnly) {
                getFromCache()
            } else {
                null
            }
            if(updatePolicy == NetworkPrimary) {
                cacheEmitted = true
            }
            if (cache != null && updatePolicy != NetworkPrimary) {
                emit(DataState.success(cache))
                cacheEmitted = true
            }

            val remote: T? =
                when (updatePolicy) {
                    CacheOnly -> null
                    CachePrimary -> {
                        if (cache == null) {
                            getFromRemoteAndUpdateCache()
                        } else {
                            null
                        }
                    }
                    is NetworkModifiedThreshold -> {
                        if (meetsThreshold(cache, updatePolicy.threshold)) {
                            getFromRemoteAndUpdateCache()
                        } else {
                            null
                        }
                    }
                    CacheUntilNetwork,
                    NetworkOnly,
                    NetworkPrimary -> {
                        getFromRemoteAndUpdateCache()
                    }
            }

            remote?.let {
                emit(DataState.success(it))
            }

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
            if(!cacheEmitted && cache != null) {
                emit(DataState.success(cache))
            }
        } catch (illegalArgument: IllegalArgumentException) {
            Log.e(TAG, illegalArgument.message ?: "unknown illegal argument error")
        }
    }

    override fun insertAlarm(alarm: Alarm): Flow<DataState<Long>> = flow {
        try {
            emit(DataState.loading())
            emit(DataState.success(launchDao.insertAlarm(alarm.mapToEntity())))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun insertAlarms(alarms: List<Alarm>): Flow<DataState<LongArray>> = flow {
        try {
            emit(DataState.loading())
            val ret = launchDao.insertAlarms(alarms.map{ it.mapToEntity() })
            emit(DataState.success(ret))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override fun insertLog(log: LogEntry): Flow<DataState<Long>> = flow {
        try {
            val ret = launchDao.insertLog(log.mapToEntity())
            emit(DataState.success(ret))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getLaunchRemoteAndUpdateCache(id: String): Launch {
        val launch = launchService.launch(id).mapToDomainModel()
        launchDao.insertLaunch(launch.mapToEntity())
        return launch
    }

    private suspend fun getLaunchFromCache(id: String): Launch? {
        return launchDao.getLaunch(id)?.mapToDomainModel()
    }

    private suspend fun getUpcomingFromCache(limit: Int, offset: Int): List<Launch> {
        return launchDao.getUpcoming(limit, offset).map { launch -> launch.mapToDomainModel() }
    }

    private suspend fun getUpcomingRemoteAndUpdateCache(limit: Int, offset: Int): List<Launch> {
        val upcoming = getUpcomingRemote(limit, offset)
        updateCacheUpcoming(upcoming)
        return upcoming
    }

    private suspend fun updateCacheUpcoming(upcoming: List<Launch>) {
        launchDao.insertLaunches(upcoming.map { launch -> launch.mapToEntity() })
    }

    private suspend fun getUpcomingRemote(limit: Int, offset: Int): List<Launch> {
        return launchService.upcoming(
            limit,
            offset
        ).launches.map { launch -> launch.mapToDomainModel() }
    }
}