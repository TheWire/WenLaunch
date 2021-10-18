package com.thewire.wenlaunch.ui.launch_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repository: LaunchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val launches: MutableState<List<Launch>> = mutableStateOf(listOf())

    fun onEvent(event: LaunchListEvent) {

    }
}