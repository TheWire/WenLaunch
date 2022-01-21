package com.thewire.wenlaunch.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.thewire.wenlaunch.domain.model.Launch
import com.thewire.wenlaunch.presentation.navigation.Screen
import com.thewire.wenlaunch.ui.launch_list.LaunchListEvent

@Composable
fun LaunchList(
    modifier : Modifier = Modifier,
    launches: List<Launch>,
    navController: NavController,
    onMoreLaunches: (LaunchListEvent) -> Unit,
    refreshCallback: (()-> Unit) -> Unit,
) {
    RefreshContainer(
        modifier = modifier
            .fillMaxSize(),
        refreshCallback = refreshCallback

    ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background),
            ) {
                itemsIndexed(
                    items = launches
                ) { index, launch ->
                        if((index + 1) >= (launches.size)) {
                            onMoreLaunches(LaunchListEvent.MoreLaunches((index + 2) - launches.size))
                        }
                        LaunchCard(
                            modifier = Modifier
                                .fillMaxWidth(),
                            launch = launch,
                            onClick = {
                                val bundle = Bundle()
                                bundle.putString("launchId", launch.id)
                                val route = Screen.LaunchView.route + "/${launch.id}"
                                navController.navigate(route)
                            }
                        )
                }
            }
        }
}