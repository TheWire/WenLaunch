package com.thewire.wenlaunch.presentation.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.thewire.wenlaunch.domain.model.Launch

@Composable
fun LaunchCard(
    launch: Launch
) {
    Text(text = launch.name ?: "")
}