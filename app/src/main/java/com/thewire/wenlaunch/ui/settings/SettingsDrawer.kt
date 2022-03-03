package com.thewire.wenlaunch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thewire.wenlaunch.ui.settings.SettingsEvent
import com.thewire.wenlaunch.ui.settings.SettingsViewModel

@Composable
fun SettingsDrawer(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    Column() {
        Text("Settings")
        SwitchButton(
            text = "Dark Mode",
            checked = viewModel.darkMode.value,
            onCheckedChanged = { viewModel.onEvent(SettingsEvent.DarkMode) }
        )
        SwitchButton(
            text = "Notifications",
            checked = false,
            onCheckedChanged = { state -> viewModel.onEvent(SettingsEvent.NotificationsAllOn) }

        )
    }
}

@Composable
fun SwitchButton(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = text
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged
        )
    }
}