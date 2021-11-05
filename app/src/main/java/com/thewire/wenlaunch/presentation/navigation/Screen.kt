package com.thewire.wenlaunch.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object LaunchList: Screen("launchList")

    object LaunchView: Screen("LaunchView")
}