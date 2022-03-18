package com.thewire.wenlaunch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel
import com.thewire.wenlaunch.domain.model.settings.NotificationLevel.*
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.theme.Typography
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import com.thewire.wenlaunch.ui.settings.SettingsEvent
import com.thewire.wenlaunch.ui.settings.SettingsViewModel

@Composable
fun SettingsDrawer(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Settings",
            modifier = Modifier
                .padding(bottom = 6.dp),
            style = Typography.h5
        )
        Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)
        val switchModifier = Modifier.fillMaxWidth()
        SwitchButton(
            modifier = switchModifier,
            text = "Dark Mode",
            checked = viewModel.darkMode.value,
            onCheckedChanged = { viewModel.onEvent(SettingsEvent.DarkMode) }
        )
        Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)
        Text(
            text = "Notifications",
            modifier = Modifier
                .padding(top = 6.dp),
            style = Typography.h6
        )
        SwitchButton(
            modifier = switchModifier,
            text = "All Notifications On",
            checked = viewModel.notifications.value.containsValue(true),
            onCheckedChanged = {
                    state -> viewModel.onEvent(SettingsEvent.NotificationsToggle(state))
            }
        )
        SwitchButton(
            modifier = switchModifier,
            text = "24 Hours",
            checked = viewModel.notifications.value[HOURS24] ?: false,
            onCheckedChanged = {
                    state -> viewModel.onEvent(SettingsEvent.NotificationsChange(HOURS24, state))
            }
        )
        SwitchButton(
            modifier = switchModifier,
            text = "1 Hour",
            checked = viewModel.notifications.value[HOURS1] ?: false,
            onCheckedChanged = {
                    state -> viewModel.onEvent(SettingsEvent.NotificationsChange(HOURS1, state))
            }

        )
        SwitchButton(
            modifier = switchModifier,
            text = "10 Minutes",
            checked = viewModel.notifications.value[MINUTES10] ?: false,
            onCheckedChanged = {
                    state -> viewModel.onEvent(SettingsEvent.NotificationsChange(MINUTES10, state))
            }
        )
//        SwitchButton(
//            modifier = switchModifier,
//            text = "Webast",
//            checked = viewModel.notifications.value[WEBCAST] ?: false,
//            onCheckedChanged = {
//                    state -> viewModel.onEvent(SettingsEvent.NotificationsChange(WEBCAST, state))
//            }
//
//        )
        SwitchButton(
            modifier = switchModifier,
            text = "Launch",
            checked = viewModel.notifications.value[LAUNCH] ?: false,
            onCheckedChanged = {
                    state -> viewModel.onEvent(SettingsEvent.NotificationsChange(LAUNCH, state))
            }
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
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colors.primaryVariant,
                uncheckedTrackColor = MaterialTheme.colors.primaryVariant,
                checkedThumbColor = MaterialTheme.colors.primary
            )
        )
    }
}

//@Preview
//@Composable
//fun SettingsDrawPreview() {
//    WenLaunchTheme(darkTheme = false) {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            SettingsDrawer(
//                modifier = Modifier.fillMaxSize().padding(6.dp),
//                viewModel =
//            )
//        }
//    }
//}