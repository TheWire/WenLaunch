package com.thewire.wenlaunch.ui.launch_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
) {
    val launches = viewModel.launches.value

    WenLaunchTheme(
        darkTheme = darkTheme
    ) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(10f)
                        .background(color = MaterialTheme.colors.background)
                ) {
                    Text(
                        "Upcoming Launches",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(MAIN_COLUMN_PADDING.dp)
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = MAIN_COLUMN_PADDING.dp)
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
}
