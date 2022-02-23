package com.thewire.wenlaunch.ui.launch_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thewire.wenlaunch.presentation.components.LaunchList
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme


const val MAIN_COLUMN_PADDING = 6

@Composable
fun LaunchListScreen(
    darkTheme: Boolean,
    navController: NavController,
    viewModel: LaunchListViewModel,
    toggleDarkMode: () -> Unit,
) {
    val launches = viewModel.launches.value

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
                                onClick = toggleDarkMode
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "menu"
                                )
                            }
                        }
                    }
                )
            }
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
