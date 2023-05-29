package com.example.musicplayer.ui.component.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.ui.component.home.HomeScreen
import com.example.musicplayer.ui.component.player.MediaScreenPlayer

@Composable
fun BottomNavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route
        ) {
            composable(route = BottomNavItem.Home.route) {
                HomeScreen(
                    onItemSelected = { id ->
                        navController.navigate("detail/$id")
                    },
                    modifier = Modifier.padding(innerPadding))
            }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })

            ) { navBackStackEntry ->
                navBackStackEntry.arguments?.getInt("id")?.let { id ->
                    MediaScreenPlayer(
                        id = id,
                    )
                }
            }
        }
    }
}