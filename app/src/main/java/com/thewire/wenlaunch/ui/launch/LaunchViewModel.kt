package com.thewire.wenlaunch.ui.launch

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.ui.launch.LaunchEvent.*
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel
@Inject
constructor(
        private val launchRepository: LaunchRepository,
): ViewModel() {
    val launch: MutableState<Launch?> = mutableStateOf(null)

    fun onEvent(event: LaunchEvent) {
        viewModelScope.launch {
            try {

                when (event) {
                    is GetLaunch -> {
                        getLaunch(event.id)
                    }
                }
            } catch(e: Exception) {
                Log.e(TAG, "onEvent: Error $e ${e.cause}")
            }
        }

    }

    private suspend fun getLaunch(id: String) {
        launch.value = launchRepository.launch(id)
    }
}