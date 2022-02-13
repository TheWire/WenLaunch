package com.thewire.wenlaunch.ui.launch

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.thewire.wenlaunch.presentation.components.LaunchView
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme

@Composable
fun LaunchScreen(
    launchId: String?,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
    viewModel: LaunchViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    DisposableEffect(key1 = viewModel) {

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


        ) {
            if (launch == null) {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            } else {
                LaunchView(
                    modifier = Modifier.fillMaxWidth(),
                    launch = launch,
                    countdown = viewModel.countdownState.value
                )
            }
        }
    }
}
