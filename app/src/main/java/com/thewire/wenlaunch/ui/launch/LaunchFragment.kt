package com.thewire.wenlaunch.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.thewire.wenlaunch.presentation.BaseApplication
import com.thewire.wenlaunch.presentation.components.LaunchView
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import javax.inject.Inject

class LaunchFragment : Fragment() {
    @Inject
    lateinit var application : BaseApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                WenLaunchTheme() {
                    Scaffold() {
                        LaunchView()
                    }
                }
            }
        }
    }
}