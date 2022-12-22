package com.thewire.wenlaunch.ui.launch

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.components.launch.LaunchComposable
import com.thewire.wenlaunch.presentation.components.media.VideoPlayer
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme

private const val TAG = "LAUNCH_SCREEN"

@Composable
fun LaunchScreen(
    launchId: String?,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
    viewModel: LaunchViewModel,
    navController: NavController,
    launchInFullscreen: Boolean = false
) {
    val context = LocalContext.current
    DisposableEffect(viewModel) {

        if (launchId == null) {
            navController.popBackStack()
            Toast.makeText(context, "Error launch not found", Toast.LENGTH_SHORT).show()
        } else {
            if (viewModel.launch.value == null) {
                viewModel.onEvent(LaunchEvent.GetLaunch(launchId))
            }
        }

        viewModel.onEvent(LaunchEvent.Start)

        onDispose {
            viewModel.onEvent(LaunchEvent.Stop)
        }
    }

    val launch = viewModel.launch.value

    WenLaunchTheme(darkTheme = darkMode) {
        val onFullScreenChange: () -> Unit = if (launchInFullscreen) {
            { navController.popBackStack() }
        } else {
            { viewModel.fullscreen.value = false }
        }

        if (launch == null && (viewModel.fullscreen.value || launchInFullscreen)) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        if (
            (launch != null && (viewModel.fullscreen.value || launchInFullscreen))
        ) {
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                videoUri = launch.vidUrls[0].uri,
                videoSeconds = viewModel.videoSeconds,
                videoState = viewModel.videoState,
                startFullScreen = true,
                fullScreenCallback = onFullScreenChange
            )
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = launch?.mission?.name ?: "unknown",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        },
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


            ) { paddingValues ->
                if (launch == null) {
                    LoadingAnimation(modifier = Modifier.fillMaxSize())
                } else {
                    LaunchComposable(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth(),
                        launch = launch,
                        viewModel = viewModel,
                    )
                }
            }
        }

    }
}
