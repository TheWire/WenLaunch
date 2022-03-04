package com.thewire.wenlaunch.ui.launch_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thewire.wenlaunch.presentation.components.LaunchList
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import com.thewire.wenlaunch.ui.SettingsDrawer
import kotlinx.coroutines.launch


const val MAIN_COLUMN_PADDING = 6

@Composable
fun LaunchListScreen(
    darkTheme: Boolean,
    navController: NavController,
    viewModel: LaunchListViewModel,
) {
    val launches = viewModel.launches.value

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    WenLaunchTheme(
        darkTheme = darkTheme
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Upcoming Launches",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .padding(MAIN_COLUMN_PADDING.dp)
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    actions = {
                        Box() {
                            IconButton(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "menu"
                                )
                            }
                        }
                    }
                )
            },
            scaffoldState = rememberScaffoldState(
                drawerState = drawerState,
            ),
            drawerContent = {
                SettingsDrawer(
                    modifier = Modifier.padding(6.dp),
                   viewModel = viewModel.settingsViewModel
                )
            },
            drawerGesturesEnabled = drawerState.isOpen,
        ) {
            if (launches.isEmpty()) {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            } else {
                LaunchList(
                    launches = launches,
                    navController = navController,
                    onMoreLaunches = { event ->
                        viewModel.onEvent(event)
                    },
                    refreshCallback = { callback ->
                        viewModel.onEvent(LaunchListEvent.RefreshLaunches(callback))
                    },
                )
            }
        }
    }
}
