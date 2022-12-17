package com.thewire.wenlaunch.ui.launch

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.components.launch.LaunchComposable
import com.thewire.wenlaunch.presentation.navigation.Screen
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme

private const val TAG = "LAUNCH_SCREEN"

@Composable
fun LaunchScreen(
    launchId: String?,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
    viewModel: LaunchViewModel,
    navController: NavController,
) {
    Log.i(TAG, "launch screen ${viewModel.fullscreen.value}")
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
        Log.i(TAG, "recompose $viewModel ${viewModel.fullscreen.value}")
        if(viewModel.fullscreen.value) {
            LaunchFullScreen(
                launchId = launchId,
                darkMode = darkMode,
                viewModel = viewModel,
                onExitFullScreen = { viewModel.fullscreen.value = false },
                navController = navController
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
                    ) {
                        val route =
                            Screen.LaunchWebcast.route + "/${launch.id}?videoState=${viewModel.videoState.value}?seconds=${viewModel.videoSeconds.value}"
                        println(route)
                        navController.navigate(route)
                    }
                }
            }
        }

    }
}
