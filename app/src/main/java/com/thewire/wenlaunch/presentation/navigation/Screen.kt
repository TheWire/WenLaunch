package com.thewire.wenlaunch.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object LaunchList: Screen("launchList")

    object LaunchDetails: Screen("launch")

    object LaunchWebcast: Screen("webcast")
}