package com.example.musicplayer.component.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.component.player.viewmodel.MediaViewModel
import com.example.musicplayer.component.player.viewmodel.UIState
//noinspection SuspiciousImport
import android.R
import com.example.musicplayer.component.player.viewmodel.UIEvent

@Composable
internal fun MediaScreenPlayer(
    id : Int,
    vm: MediaViewModel = hiltViewModel(),
    navController: NavController,
    startService: () -> Unit,
) {

    val state = vm.uiState.collectAsState()
    val uiStatePlayer by vm.uiStatePlayer.collectAsState()

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

                MediaPlayerContent(
                    navController = navController,
                    formatDuration = uiStatePlayer.formatDuration,
                    duration =  uiStatePlayer.duration,
                    isPlaying = uiStatePlayer.isPlaying,
                    progress = uiStatePlayer.progress,
                    progressString = uiStatePlayer.progressString,
                    onUIEvent = uiStatePlayer.onUIEvent
                )
            }
        }

    }
}

@Composable
private fun MediaPlayerContent(
    navController: NavController,
    formatDuration: (Long) -> String,
    duration: Long,
    isPlaying: Boolean,
    progress: Float,
    progressString: String,
    onUIEvent: (UIEvent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MediaPlayerUI(
            durationString = formatDuration(duration),
            playResourceProvider = {
                if (isPlaying) R.drawable.ic_media_pause
                else R.drawable.ic_media_play
            },
            progressProvider = { Pair(progress, progressString) },
            onUiEvent = onUIEvent,
        )

    }
}