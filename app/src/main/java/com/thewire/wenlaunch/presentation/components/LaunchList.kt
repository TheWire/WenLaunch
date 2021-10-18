package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thewire.wenlaunch.domain.model.Launch

@Composable
fun LaunchList(
    launches: List<Launch>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        LazyColumn() {
            itemsIndexed(
                items = launches
            ) { _, launch ->
                LaunchCard(launch)
            }
        }
    }
}