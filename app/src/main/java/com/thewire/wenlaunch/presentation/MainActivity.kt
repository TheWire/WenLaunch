package com.thewire.wenlaunch.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.thewire.wenlaunch.presentation.navigation.Screen
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme
import com.thewire.wenlaunch.ui.launch.LaunchFullScreen
import com.thewire.wenlaunch.ui.launch.LaunchScreen
import com.thewire.wenlaunch.ui.launch.LaunchViewModel
import com.thewire.wenlaunch.ui.launch_list.LaunchListScreen
import com.thewire.wenlaunch.ui.launch_list.LaunchListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
        setContent {

            WenLaunchTheme(darkTheme = (application as BaseApplication).darkMode.value) {
                val systemUiController = rememberSystemUiController()
                val color = MaterialTheme.colors.primary
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = color
                    )
                }
            }

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.LaunchList.route) {
                composable(route = Screen.LaunchList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: LaunchListViewModel =
                        viewModel(viewModelStoreOwner, "LaunchListViewModel", factory)
                    LaunchListScreen(
                        darkTheme = (application as BaseApplication).darkMode.value,
                        navController = navController,
                        viewModel = viewModel,
                    )

                }

                composable(
                    route = Screen.LaunchDetails.route + "/{launchId}",
                    arguments = listOf(
                        navArgument("launchId") { type = NavType.StringType },
                    ),
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: LaunchViewModel =
                        viewModel(viewModelStoreOwner, "LaunchViewModel", factory)
                    lifecycle.addObserver(viewModel)
                    LaunchScreen(
                        launchId = navBackStackEntry.arguments?.getString("launchId"),
                        darkMode = (application as BaseApplication).darkMode.value,
                        toggleDarkMode = (application as BaseApplication)::toggleDarkTheme,
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
                composable(
                    route = Screen.LaunchWebcast.route + "/{launchId}?videoState={videoState}&?seconds={seconds}",
                    arguments = listOf(
                        navArgument("launchId") { type = NavType.StringType },
                        navArgument("videoState") { type = NavType.StringType },
                        navArgument("seconds") { type = NavType.FloatType },
                    ),
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: LaunchViewModel =
                        viewModel(viewModelStoreOwner, "LaunchViewModel", factory)
                    navBackStackEntry.arguments?.getString("videoState")?.let { state ->
                        viewModel.videoState.value = state
                    }
                    navBackStackEntry.arguments?.getFloat("seconds")?.let { seconds ->
                        viewModel.videoSeconds.value = seconds
                    }
                    LaunchFullScreen(
                        launchId = navBackStackEntry.arguments?.getString("launchId"),
                        darkMode = (application as BaseApplication).darkMode.value,
                        viewModel = viewModel,
                        navController = navController,
                        onExitFullScreen = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}