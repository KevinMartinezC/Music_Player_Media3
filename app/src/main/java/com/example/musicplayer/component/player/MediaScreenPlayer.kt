package com.example.musicplayer.component.player

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.component.player.viewmodel.MediaViewModel
import com.example.musicplayer.component.player.viewmodel.UIState

@Composable
internal fun MediaScreenPlayer(
    id : Int,
    vm: MediaViewModel,
    navController: NavController,
    startService: () -> Unit,
) {

    val state = vm.uiState.collectAsState()
    // Call loadData with the passed URL when the Composable is first launched
    LaunchedEffect(Unit) {
        vm.loadData(id)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.value) {
            UIState.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )
            is UIState.Ready -> {
                LaunchedEffect(true) {
                    startService()
                }

                ReadyContent(vm = vm, navController = navController)
            }
        }

    }
}

@Composable
private fun ReadyContent(
    vm: MediaViewModel,
    navController: NavController,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MediaPlayerUI(
            durationString = vm.formatDuration(vm.duration),
            playResourceProvider = {
                if (vm.isPlaying) R.drawable.ic_media_pause
                else R.drawable.ic_media_play
            },
            progressProvider = { Pair(vm.progress, vm.progressString) },
            onUiEvent = vm::onUIEvent,
        )

    }
}