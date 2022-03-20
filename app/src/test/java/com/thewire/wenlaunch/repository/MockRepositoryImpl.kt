package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRepositoryImpl : ILaunchRepository {


    override fun upcoming(
        limit: Int,
        offset: Int,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<List<Launch>>> = flow {
        emit(DataState.loading())
        delay(1000)
        emit(DataState.success(MockLaunchData.launches))
    }

    override fun launch(
        id: String,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<Launch?>> = flow {
        emit(DataState.loading())
        delay(1000)
        emit(DataState.success(MockLaunchData.launches[0]))
    }
}