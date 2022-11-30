package com.thewire.wenlaunch.repository

import com.thewire.wenlaunch.Logging.model.LogEntry
import com.thewire.wenlaunch.domain.DataState
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.logging.MockLogger
import com.thewire.wenlaunch.notifications.model.Alarm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val TAG= "MOCK_LOGGER"
class MockRepositoryImpl() : ILaunchRepository {

    val Log = MockLogger()
    var alarms = listOf<Alarm>()
    override fun upcoming(
        limit: Int,
        offset: Int,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<List<Launch>>> = flow {
        delay(1000)
        emit(DataState.success(MockLaunchData.launches))
    }

    override fun launch(
        id: String,
        updatePolicy: LaunchRepositoryUpdatePolicy
    ): Flow<DataState<Launch?>> = flow {
        delay(1000)
        emit(DataState.success(MockLaunchData.launches[0]))
    }

    override fun alarm(requestId: Int): Flow<DataState<Alarm>> = flow {
        val ret = alarms.find { it.requestId == requestId }
        if(ret == null) {
            emit(DataState.error("can not find alarm"))
        } else {
            emit(DataState.success(ret))
        }
    }

    override fun alarmsAll(): Flow<DataState<List<Alarm>>> = flow {
        emit(DataState.success(alarms))
    }

    override fun alarmsOfLaunch(launchId: String): Flow<DataState<List<Alarm>>> = flow {
        val ret = alarms.filter { it.launchId == launchId }
        emit(DataState.success(ret))
    }

    override fun deleteAllAlarms(): Flow<DataState<Int>> = flow {
        val size = alarms.size
        alarms = listOf()
        emit(DataState.success(size))
    }

    override fun deleteAlarm(requestId: Int): Flow<DataState<Int>> = flow {
        val size = alarms.size
        alarms = alarms.filter {
            it.requestId == requestId
        }
        if(size == alarms.size) {
            emit(DataState.error("can not find alarm"))
        } else {
            emit(DataState.success(1))
        }
    }

    override fun deleteOldLaunches(): Flow<DataState<Int>> {
        TODO("Not yet implemented")
    }

    override fun deleteAlarmsOfLaunch(launchId: String): Flow<DataState<Int>> = flow {
        val size = alarms.size
        alarms = alarms.filter {
            it.launchId != launchId
        }
        if(size == alarms.size) {
            emit(DataState.error("no alarms deleted"))
        } else {
            emit(DataState.success(1))
        }

    }

    override fun insertAlarm(alarm: Alarm): Flow<DataState<Long>> = flow {
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

    override fun insertLog(log: LogEntry): Flow<DataState<Long>> = flow{
        //do nothing
    }
}