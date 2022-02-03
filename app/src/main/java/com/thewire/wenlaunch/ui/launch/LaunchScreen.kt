package com.thewire.wenlaunch.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextOverflow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.components.LaunchView
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Composable
fun LaunchScreen(
    launchId: String?,
    darkMode: Boolean,
    toggleDarkMode: () -> Unit,
    viewModel: LaunchViewModel,
    navController: NavController,
) {

    if (launchId == null) {
        TODO("invalid launch")
    } else {
        if (viewModel.launch.value == null) {
            viewModel.onEvent(LaunchEvent.GetLaunch(launchId))
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
                    countdown = viewModel.countdown?.observeAsState()
                )
            }
        }
    }
}
