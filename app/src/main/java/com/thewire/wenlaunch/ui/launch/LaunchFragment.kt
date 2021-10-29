package com.thewire.wenlaunch.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.components.LaunchView
import com.thewire.wenlaunch.presentation.components.LoadingAnimation
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchFragment : Fragment() {
    @Inject
    lateinit var application : BaseApplication

    private val viewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("launchId")?.let { launchId ->
            viewModel.onEvent(LaunchEvent.GetLaunch(launchId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val launch = viewModel.launch.value

                val navController = findNavController()
                WenLaunchTheme(darkTheme = application.darkMode.value) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = launch?.name?: "unknown") },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { navController.popBackStack()}
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
                                            onClick = { application.toggleDarkTheme() }
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
                        if(launch == null) {
                            LoadingAnimation(modifier = Modifier.fillMaxSize())
                        } else {
                            LaunchView(modifier = Modifier.fillMaxWidth(), launch = launch)
                        }
                    }
                }
            }
        }
    }
}