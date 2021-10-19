package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.thewire.wenlaunch.domain.model.Launch

@Composable
fun LaunchView(
    launch: Launch
) {
    Column() {
        Text(launch.name ?: "error")
    }
}