package com.thewire.wenlaunch.ui.launch

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewire.wenlaunch.di.IDispatcherProvider
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.repository.LaunchRepository
import com.thewire.wenlaunch.ui.launch.LaunchEvent.*
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel
@Inject
constructor(
    private val launchRepository: LaunchRepository,
    private val dispatcherProvider: IDispatcherProvider,
) : ViewModel(), DefaultLifecycleObserver {
    val launch: MutableState<Launch?> = mutableStateOf(null)
    val countdownState = mutableStateOf<DateTimePeriod?>(null)
    var launchCountdown: LaunchCountdown? = null
    fun onEvent(event: LaunchEvent) {
        try {
            when (event) {
                Start -> {}
                Stop -> stopCountdown()
                is GetLaunch -> {
                    getLaunch(event.id)
                }
                is StartCountdown -> {
                    startCountdown(event.launchTime)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onEvent: Error $e ${e.cause}")
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        resumeCountdown()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        resumeCountdown()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        pauseCountdown()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        pauseCountdown()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        stopCountdown()
    }

    private fun resumeCountdown() {
        launchCountdown?.resume()
    }

    private fun pauseCountdown() {
        launchCountdown?.pause()
    }

    private fun stopCountdown() {
        launchCountdown?.stop()
    }

    private fun startCountdown(launchTime: ZonedDateTime) {
        launchCountdown =
            LaunchCountdown(launchTime, dispatcherProvider = dispatcherProvider).apply {
                viewModelScope.launch {
                    start().collect { dateTimePeriod ->
                        countdownState.value = dateTimePeriod
                    }
                }
            }
    }

    private fun getLaunch(id: String) {
        viewModelScope.launch {
            launchRepository.launch(id).onEach { dataState ->
                if(dataState.loading) {
                    TODO()
                }
                dataState.data?.let { newLaunch ->
                    launch.value = newLaunch
                    if (newLaunch.status?.abbrev == LaunchStatus.GO) {
                        startCountdown(newLaunch.net)
                    }
                }

                dataState.error?.let { error ->
                    Log.e(TAG, "Exception: $error")
                }
            }
        }
    }
}