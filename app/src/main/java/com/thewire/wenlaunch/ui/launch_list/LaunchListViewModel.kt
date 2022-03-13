package com.thewire.wenlaunch.ui.launch_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.ui.settings.SettingsViewModel
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val INITIAL_LAUNCH_NUM = 5

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repository: LaunchRepository,
    private val savedStateHandle: SavedStateHandle,
    val settingsViewModel: SettingsViewModel,
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                getUpcoming(INITIAL_LAUNCH_NUM)
            } catch (e: Exception) {
                Log.e(TAG, "Exception: $e, ${e.cause}")
            }
        }
    }

    val launches: MutableState<List<Launch>> = mutableStateOf(listOf())

    fun onEvent(event: LaunchListEvent) {
        viewModelScope.launch {
            when (event) {
                is LaunchListEvent.RefreshLaunches -> {
                    getUpcoming(launches.value.size)
                    event.callback()
                }
                is LaunchListEvent.MoreLaunches -> {
                    getMoreLaunches(event.numLaunches)
                }
            }
        }
    }

    private suspend fun getUpcoming(limit: Int) {
        repository.upcoming(limit, 0).onEach { dataState ->
            if(dataState.loading) {
                TODO()
            }
            dataState.data?.let { upcoming ->
                launches.value = upcoming
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
            }
        }
    }

    private suspend fun getMoreLaunches(numLaunches: Int) {
        repository.upcoming(numLaunches, launches.value.size).onEach { dataState ->
            if(dataState.loading) {
                TODO()
            }
            dataState.data?.let { upcoming ->
                val currentList = ArrayList(launches.value)
                currentList.addAll(upcoming)
                launches.value = currentList
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
            }
        }
    }
}