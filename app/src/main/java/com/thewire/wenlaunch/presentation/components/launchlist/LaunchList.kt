package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.presentation.components.layout.LazyListOrientationLayout
import com.thewire.wenlaunch.presentation.navigation.Screen
import com.thewire.wenlaunch.ui.launch_list.LaunchListEvent

@Composable
fun LaunchList(
    modifier: Modifier = Modifier,
    launches: List<Launch>,
    navController: NavController,
    onMoreLaunches: (LaunchListEvent) -> Unit,
    refreshCallback: (() -> Unit) -> Unit,
) {
    RefreshContainer(
        modifier = modifier
            .fillMaxSize(),
        refreshCallback = refreshCallback

    ) {
        LazyListOrientationLayout(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background),
            portraitMaxHeight = 300.dp,
            landscapeMaxWidth = 500.dp,
            landscapeMinWidth = 400.dp
        ) { modifier ->
            itemsIndexed(
                items = launches
            ) { index, launch ->
                if ((index + 1) >= (launches.size)) {
                    onMoreLaunches(LaunchListEvent.MoreLaunches((index + 2) - launches.size))
                }
                if (launch.status?.abbrev != LaunchStatus.SUCCESS) {
                    LaunchCard(
                        modifier = modifier,
                        launch = launch,
                        onClick = {
                            val route = Screen.LaunchView.route + "/${launch.id}"
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    }
}