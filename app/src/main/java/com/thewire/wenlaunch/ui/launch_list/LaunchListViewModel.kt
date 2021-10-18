package com.thewire.wenlaunch.ui.launch_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repository: LaunchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                getUpcoming()
            }catch(e: Exception) {
                Log.e("fetch error", "Exception: $e, ${e.cause}")
            }
        }
    }
    val launches: MutableState<List<Launch>> = mutableStateOf(listOf())

    fun onEvent(event: LaunchListEvent) {

    }

    private suspend fun getUpcoming() {
        Log.d("upcoming", "getUpcoming")
        val result = repository.upcoming(10)
        for(r in result) {
            println(r.name)
        }
        launches.value = result
    }
}