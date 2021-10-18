package com.thewire.wenlaunch.ui.launch_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.components.LaunchList
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchListFragment : Fragment() {
    @Inject
    lateinit var application: BaseApplication

    private val viewModel: LaunchListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val launches = viewModel.launches.value

                WenLaunchTheme(

                ) {
                    Scaffold(

                    ) {
                        LaunchList(launches = launches)
                    }
                }
            }
        }
    }
}