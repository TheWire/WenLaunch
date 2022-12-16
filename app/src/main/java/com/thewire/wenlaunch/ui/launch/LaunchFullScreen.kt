package com.thewire.wenlaunch.ui.launch

import android.content.pm.ActivityInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.thewire.wenlaunch.presentation.components.media.VideoPlayer
import com.thewire.wenlaunch.presentation.findActivity
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme

private const val TAG = "LAUNCH_SCREEN"

@Composable
fun LaunchFullScreen(
    launchId: String?,
    darkMode: Boolean,
    viewModel: LaunchViewModel,
    onExitFullScreen: () -> Unit,
    navController: NavController,
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        if (launchId == null) {
            navController.popBackStack()
            Toast.makeText(context, "Error launch not found", Toast.LENGTH_SHORT).show()
        } else {
            if (viewModel.launch.value == null) {
                viewModel.onEvent(LaunchEvent.GetLaunch(launchId))
            }
        }
    }
    val activity = LocalContext.current.findActivity()
    val systemUiController: SystemUiController = rememberSystemUiController()

    val launch = viewModel.launch.value

    WenLaunchTheme(darkTheme = darkMode) {
        Log.i(TAG, "recompose $viewModel ${viewModel.fullscreen.value}")
        if(launch != null //&&
//            launch.webcastLive &&
//            launch.vidUrls.isNotEmpty()
        ) {

            LaunchedEffect(viewModel) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                systemUiController.isSystemBarsVisible = false
            }
            DisposableEffect(viewModel) {
                onDispose {
                    Log.i(TAG, "onDispose")
                    systemUiController.isSystemBarsVisible = true
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }

            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                videoUri = "https://www.youtube.com/watch?v=VsacL7_yDSo",//launch.vidUrls[0].uri,
                videoSeconds = viewModel.videoSeconds,
                videoState = viewModel.videoState,
                fullScreenCallback = onExitFullScreen
            )
        }
    }
}
