package com.thewire.wenlaunch.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thewire.wenlaunch.R
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.components.LaunchView
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
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val launch = viewModel.launch.value
                WenLaunchTheme() {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = launch?.name?: "unknown") },
                                navigationIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "back"
                                    )
                                }
                            )
                        }


                            ) {
                            if(launch == null) {
                                Text("loading")
                            } else {
                                launch?.let {
                                    LaunchView(it)
                                }

                            }

                        }
                }
            }
        }
    }
}