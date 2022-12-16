package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.domain.model.LaunchStatus
import com.thewire.wenlaunch.presentation.components.layout.LazyListOrientationLayout
import com.thewire.wenlaunch.presentation.navigation.Screen
import com.thewire.wenlaunch.ui.launch_list.LaunchListEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchList(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    launches: List<Launch>,
    navController: NavController,
    onMoreLaunches: (LaunchListEvent) -> Unit,
    refreshing: MutableState<Boolean>,
    onRefresh: () -> Unit,
) {

    val state = rememberPullRefreshState(refreshing = refreshing.value, onRefresh = onRefresh)

    Box(
        modifier = modifier
            .pullRefresh(state)
            .fillMaxSize(),
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
                if ((index + 1) >= launches.size && !loading) {
                    onMoreLaunches(LaunchListEvent.MoreLaunches((index + 2) - launches.size))
                }
                if (launch.status?.abbrev != LaunchStatus.SUCCESS) {
                    LaunchCard(
                        modifier = modifier,
                        launch = launch,
                        onClick = { fullscreen ->
                            val route = if(fullscreen) {
                                Screen.LaunchWebcast.route
                            } else {
                                Screen.LaunchDetails.route
                            } + "/${launch.id}"
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing.value,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}