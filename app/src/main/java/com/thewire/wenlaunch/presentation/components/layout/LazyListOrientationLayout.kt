package com.thewire.wenlaunch.presentation.components.layout

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.collect

@Composable
fun LazListOrientationLayout(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    portraitMaxHeight: Dp,
    portraitMinHeight: Dp = portraitMaxHeight,
    landscapeMaxWidth: Dp,
    landscapeMinWidth: Dp = landscapeMaxWidth,
    content: LazyListScope.(Modifier) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val orientation = remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation.value = it }
    }

    when (orientation.value) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyRow(
                modifier = modifier,
            ) {
                content(
                    contentModifier.fillMaxHeight().widthIn(landscapeMinWidth, landscapeMaxWidth)
                )
            }
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            LazyColumn(
                modifier = modifier,
            ) {
                content(
                    contentModifier.fillMaxWidth().heightIn(portraitMinHeight, portraitMaxHeight)
                )
            }
        }
    }
}