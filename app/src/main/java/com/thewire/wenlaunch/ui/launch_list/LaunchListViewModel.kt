package com.thewire.wenlaunch.ui.launch_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.repository.LaunchRepositoryUpdatePolicy
import com.thewire.wenlaunch.ui.settings.SettingsViewModel
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

const val INITIAL_LAUNCH_NUM = 5

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repositoryI: ILaunchRepository,
    private val savedStateHandle: SavedStateHandle,
    val settingsViewModel: SettingsViewModel,
) : ViewModel() {

    init {
        getUpcoming(INITIAL_LAUNCH_NUM, LaunchRepositoryUpdatePolicy.CacheUntilNetwork)
    }

    val launches: MutableState<List<Launch>> = mutableStateOf(listOf())

    fun onEvent(event: LaunchListEvent) {
        when (event) {
            is LaunchListEvent.RefreshLaunches -> {
                getUpcoming(launches.value.size, LaunchRepositoryUpdatePolicy.NetworkOnly, event.callback)
            }
            is LaunchListEvent.MoreLaunches -> {
                getMoreLaunches(event.numLaunches)
            }
        }
    }

    private fun getUpcoming(limit: Int, updatePolicy: LaunchRepositoryUpdatePolicy, callback: (() -> Unit)? = null) {
        repositoryI.upcoming(limit, 0, updatePolicy).onEach { dataState ->
            if (dataState.loading) {
                println("loading")
            }
            dataState.data?.let { upcoming ->
                launches.value = upcoming
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
            }
            callback?.let {
                delay(100) //delay needed due to bug in pulldownrefreshindictor
                it()
            }
        }.launchIn(viewModelScope)
    }

    private fun getMoreLaunches(numLaunches: Int) {
        repositoryI.upcoming(numLaunches, launches.value.size).onEach { dataState ->
            if (dataState.loading) {
                println("loading")
            }
            dataState.data?.let { upcoming ->
                val currentList = ArrayList(launches.value)
                currentList.addAll(upcoming)
                launches.value = currentList
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
            }
        }.launchIn(viewModelScope)
    }
}