package com.example.musicplayer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.component.home.HomeScreen
import com.example.musicplayer.component.navigation.BottomBar
import com.example.musicplayer.component.navigation.BottomNavItem
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.data.service.MediaService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MediaViewModel by viewModels()
    private var isServiceRunning = false

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomBar(navController = navController) },
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Home.route
                        ) {
                            composable(route = BottomNavItem.Home.route) {
                                HomeScreen(modifier = Modifier.padding(innerPadding), navController = navController)
                            }
                            composable(
                                route = "detail/{id}",
                                arguments = listOf(navArgument("id") { type = NavType.IntType })

                            ) {navBackStackEntry ->
                                navBackStackEntry.arguments?.getInt("id")?.let { id ->
                                    MediaScreenPlayer(
                                        id = id,
                                        vm = viewModel,
                                        navController = navController,
                                        startService = ::startService
                                    )
                                }
                            }
                        }
                    }

                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, MediaService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }

}



