package com.thewire.wenlaunch.ui.launch

sealed class LaunchEvent {
    data class GetLaunch(
        val id: String
    ): LaunchEvent()
}
