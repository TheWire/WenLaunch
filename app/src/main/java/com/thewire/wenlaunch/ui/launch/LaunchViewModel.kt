package com.thewire.wenlaunch.ui.launch

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.ui.launch.LaunchEvent.*
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel
@Inject
constructor(
        private val launchRepository: LaunchRepository,
        private val dispatcherProvider: IDispatcherProvider,
): ViewModel() {
    val launch: MutableState<Launch?> = mutableStateOf(null)
    var countdown: LiveData<DateTimePeriod>? = null

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

    private fun startCountdown() {
        if (countdown != null) {
               return
        }
        launch.value?.net?.let { net ->
            viewModelScope.launch() {
                val launchCountdown = LaunchCountdown(net, dispatcherProvider = dispatcherProvider)
                countdown = launchCountdown.start()
            }
        }
    }

    private suspend fun getLaunch(id: String) {
        try {
            val newLaunch = launchRepository.launch(id)
            launch.value = newLaunch
            if(newLaunch.status?.abbrev == LaunchStatus.GO) {
                startCountdown()
            }
        } catch(e: Exception) {
            Log.e(TAG, "Exception: $e, ${e.cause}")
        }
    }

}