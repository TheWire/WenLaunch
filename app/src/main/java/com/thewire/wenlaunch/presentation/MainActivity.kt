package com.thewire.wenlaunch.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thewire.wenlaunch.presentation.navigation.Screen
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
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.LaunchList.route) {
                composable(route = Screen.LaunchList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: LaunchListViewModel = viewModel(viewModelStoreOwner, "LaunchListViewModel", factory)
                    LaunchListScreen(
                        darkTheme = (application as BaseApplication).darkMode.value,
                        navController = navController,
                        viewModel = viewModel,
                        toggleDarkMode = (application as BaseApplication)::toggleDarkTheme,
                    )

                }

                composable(
                    route = Screen.LaunchView.route + "/{launchId}",
                    arguments = listOf(navArgument("launchId") {
                        type = NavType.StringType
                    }),
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: LaunchViewModel = viewModel(viewModelStoreOwner, "LaunchViewModel", factory)
                    lifecycle.addObserver(viewModel)
                    LaunchScreen(
                        launchId = navBackStackEntry.arguments?.getString("launchId"),
                        darkMode = (application as BaseApplication).darkMode.value,
                        toggleDarkMode = (application as BaseApplication)::toggleDarkTheme,
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}