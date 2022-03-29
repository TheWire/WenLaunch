package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.notifications.model.Alarm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRepositoryImpl : ILaunchRepository {

    var alarms = listOf<Alarm>()
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

    override fun alarm(requestId: Int): Flow<DataState<Alarm>> = flow {
        emit(DataState.loading())
        val ret = alarms.find { it.requestId == requestId }
        if(ret == null) {
            emit(DataState.error("can not find alarm"))
        } else {
            emit(DataState.success(ret))
        }
    }

    override fun alarmsAll(): Flow<DataState<List<Alarm>>> = flow {
        emit(DataState.loading())
        emit(DataState.success(alarms))
    }

    override fun alarmsOfLaunch(launchId: String): Flow<DataState<List<Alarm>>> = flow {
        emit(DataState.loading())
        val ret = alarms.filter { it.launchId == launchId }
        if(ret == null) {
            emit(DataState.error("can not find alarm"))
        } else {
            emit(DataState.success(ret))
        }
    }

    override fun deleteAllAlarms(): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        val size = alarms.size
        alarms = listOf()
        emit(DataState.success(size))
    }

    override fun deleteAlarm(requestId: Int): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        val size = alarms.size
        alarms = alarms.filter {
            it.requestId == requestId
        }
        if(size == alarms.size) {
            emit(DataState.error("can not find alarm"))
        } else {
            1
        }
        emit(DataState.success(1))
    }

    override fun deleteAlarmsOfLaunch(launchId: String): Flow<DataState<Int>> = flow {
        emit(DataState.loading())
        val size = alarms.size
        alarms = alarms.filter {
            it.launchId != launchId
        }
        alarms = listOf()
        if(size == alarms.size) {
            emit(DataState.error("can not find alarm"))
        } else {
            1
        }
        emit(DataState.success(1))
    }

    override fun insertAlarm(alarm: Alarm): Flow<DataState<Long>> = flow {
        emit(DataState.loading())
        val newAlarms = alarms.toMutableList()
        val index = newAlarms.size
        newAlarms.add(newAlarms.size, alarm)
        alarms = newAlarms
        emit(DataState.success(index.toLong()))
    }

    override fun insertAlarms(alarms: List<Alarm>): Flow<DataState<LongArray>> = flow {
        val indexes = arrayListOf<Long>()
        emit(DataState.loading())
        val newAlarms = alarms.toMutableList()
        alarms.forEach { alarm ->
            val index = newAlarms.size
            newAlarms.add(newAlarms.size, alarm)
            indexes.add(index.toLong())
        }
        emit(DataState.success(indexes.toLongArray()))
    }
}