package com.thewire.wenlaunch.ui.launch_list

sealed class LaunchListEvent {
    object RefreshLaunches: LaunchListEvent()
    data class MoreLaunches(val numLaunches: Int): LaunchListEvent()
}
