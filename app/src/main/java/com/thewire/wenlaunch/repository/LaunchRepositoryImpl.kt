package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.cache.LaunchDao
import com.thewire.wenlaunch.cache.model.relations.mapToEntity
import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.network.LaunchService
import com.thewire.wenlaunch.network.model.mappers.LaunchDtoMapper
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy.CacheUntilNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LaunchRepositoryImpl (
    private val launchDao: LaunchDao,
    private val launchService: LaunchService,
) : LaunchRepository {
    override suspend fun upcoming(
        limit: Int,
        offset: Int,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<List<Launch>>> = flow {
        try {
            emit(DataState.loading())
            val upcomingLaunchesCache = if(updatePolicy != LaunchRepositoryUpdatePolicy.NetworkOnly) {
                getUpcomingFromCache(limit, offset)
            } else {
                null
            }
            upcomingLaunchesCache?.let { upcoming ->
                emit(DataState.success(upcoming))
            }

            val upcomingLaunchesRemote =  when(updatePolicy) {
                LaunchRepositoryUpdatePolicy.CacheOnly -> null
                LaunchRepositoryUpdatePolicy.CachePrimary -> {
                    if(upcomingLaunchesCache == null) {
                        getUpcomingRemoteAndUpdateCache(limit, offset)
                    } else {
                        null
                    }
                }
                CacheUntilNetwork -> getUpcomingRemoteAndUpdateCache(limit, offset)
                is LaunchRepositoryUpdatePolicy.NetworkModifiedThreshold -> TODO()
                LaunchRepositoryUpdatePolicy.NetworkOnly -> getUpcomingRemoteAndUpdateCache(limit, offset)
                LaunchRepositoryUpdatePolicy.NetworkPrimary -> getUpcomingRemoteAndUpdateCache(limit, offset)
            }

            upcomingLaunchesRemote?.let { upcoming ->
                emit(DataState.success(upcoming))
            }

        } catch(e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun launch(id: String, updatePolicy: LaunchRepositoryUpdatePolicy): Flow<DataState<Launch>> = flow {
//        return launchService.launch(id).mapToDomainModel()
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
        return launchService.upcoming(limit, offset).launches.map { launch -> launch.mapToDomainModel() }
    }
}