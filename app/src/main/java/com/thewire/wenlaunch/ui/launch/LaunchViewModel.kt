package com.thewire.wenlaunch.ui.launch

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.thewire.wenlaunch.repository.ILaunchRepository
import com.thewire.wenlaunch.ui.launch.LaunchEvent.*
import com.thewire.wenlaunch.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel
@Inject
constructor(
    private val ILaunchRepository: ILaunchRepository,
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
        if(launch.value?.status?.abbrev == LaunchStatus.GO) {
            launchCountdown?.resume()
        }
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
        ILaunchRepository.launch(id).onEach { dataState ->
            if (dataState.loading) {
                println("loading")
            }
            dataState.data?.let { newLaunch ->
                println()
                launch.value = newLaunch
                when (newLaunch.status?.abbrev) {
                    LaunchStatus.GO, LaunchStatus.HOLD -> startCountdown(newLaunch.net)
                    else -> {}
                }
                if(newLaunch.status?.abbrev == LaunchStatus.HOLD) {
                    pauseCountdown()
                }
            }

            dataState.error?.let { error ->
                Log.e(TAG, "Exception: $error")
            }
        }.launchIn(viewModelScope)
    }
}