package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {

    fun upcoming(
        limit: Int,
        offset: Int,
        updatePolicy: LaunchRepositoryUpdatePolicy = LaunchRepositoryUpdatePolicy.CacheUntilNetwork
    ) : Flow<DataState<List<Launch>>>

    fun launch(
        id: String,
        updatePolicy: LaunchRepositoryUpdatePolicy = LaunchRepositoryUpdatePolicy.CacheUntilNetwork
    ) : Flow<DataState<Launch?>>

}

sealed class LaunchRepositoryUpdatePolicy {
    //get from cache until network available
    object CacheUntilNetwork: LaunchRepositoryUpdatePolicy()
    //only get from cache - no network
    object CacheOnly: LaunchRepositoryUpdatePolicy()
    //only get from cache unless no data
    object CachePrimary: LaunchRepositoryUpdatePolicy()
    //only get data from network
    object NetworkOnly: LaunchRepositoryUpdatePolicy()
    //get data from network unless unavailable
    object NetworkPrimary: LaunchRepositoryUpdatePolicy()
    //get data from cache unless older than min time
    data class NetworkModifiedThreshold(val threshold: Long): LaunchRepositoryUpdatePolicy()
}