package com.example.musicplayer.component.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicplayer.component.home.HomeScreen
import com.example.musicplayer.component.player.MediaPlayerScreen


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    contentPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen(modifier = Modifier.padding(contentPadding))
        }
        composable(route = BottomNavItem.Player.route) {
            MediaPlayerScreen(modifier = Modifier.padding(contentPadding))
        }
    }
}

