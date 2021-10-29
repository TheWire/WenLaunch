package com.thewire.wenlaunch.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.domain.model.Launch
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
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
        refreshCallback = refreshCallback

    ) {
            LazyColumn() {
                itemsIndexed(
                    items = launches
                ) { index, launch ->
                    if((index + 1) >= (launches.size)) {
                        onMoreLaunches(LaunchListEvent.MoreLaunches((index + 2) - launches.size))
                    }
                    LaunchCard(
                        modifier = Modifier.fillMaxWidth(),
                        launch = launch,
                        onClick = {
                            val bundle = Bundle()
                            bundle.putString("launchId", launch.id)
                            navController.navigate(R.id.viewLaunch, bundle)
                        }
                    )
                }
            }
        }
}