package com.thewire.wenlaunch.ui.launch_list

sealed class LaunchListEvent {
    data class RefreshLaunches(val callback: () -> Unit): LaunchListEvent()
}
