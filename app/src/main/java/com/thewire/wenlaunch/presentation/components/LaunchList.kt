package com.thewire.wenlaunch.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.domain.model.Launch

@Composable
fun LaunchList(
    launches: List<Launch>,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if(launches.isEmpty()) {
            Text("Loading...")
        } else {
            LazyColumn() {
                itemsIndexed(
                    items = launches
                ) { _, launch ->
                    LaunchCard(
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
}