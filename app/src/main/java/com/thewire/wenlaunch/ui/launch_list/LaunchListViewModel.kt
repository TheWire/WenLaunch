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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
private const val TAG = "LAUNCH_LIST"
const val INITIAL_LAUNCH_NUM = 5

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repositoryI: ILaunchRepository,
    private val savedStateHandle: SavedStateHandle,
    val settingsViewModel: SettingsViewModel,
) : ViewModel() {

    val launches: MutableState<List<Launch>> = mutableStateOf(listOf())
    val refreshing = mutableStateOf(false)
    val loading = mutableStateOf(false)

    init {
        getUpcoming(INITIAL_LAUNCH_NUM, LaunchRepositoryUpdatePolicy.CacheUntilNetwork)
    }

    fun onEvent(event: LaunchListEvent) {
        when (event) {
            is LaunchListEvent.RefreshLaunches -> {
                getUpcoming(launches.value.size, LaunchRepositoryUpdatePolicy.NetworkOnly)
            }
            is LaunchListEvent.MoreLaunches -> {
                getMoreLaunches(event.numLaunches, LaunchRepositoryUpdatePolicy.NetworkPrimary)
            }
        }
    }

    private fun getUpcoming(limit: Int, updatePolicy: LaunchRepositoryUpdatePolicy) {
        repositoryI.upcoming(limit, 0, updatePolicy).onEach { dataState ->
            if (dataState.loading) {
                loading.value = true
                refreshing.value = true
            }
            dataState.data?.let { upcoming ->
                launches.value = upcoming
                loading.value = false
                refreshing.value = false
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
                loading.value = false
                refreshing.value = false
            }

        }.launchIn(viewModelScope)
    }

    private fun getMoreLaunches(numLaunches: Int, updatePolicy: LaunchRepositoryUpdatePolicy) {
        repositoryI.upcoming(numLaunches, launches.value.size, updatePolicy).onEach { dataState ->
            if (dataState.loading) {
                loading.value = true
            }
            dataState.data?.let { upcoming ->
                val currentList = ArrayList(launches.value)
                currentList.addAll(upcoming)
                launches.value = currentList
                loading.value = false
            }
            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
                loading.value = false
            }
        }.launchIn(viewModelScope)
    }
}