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
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.component.player.viewmodel.MediaViewModel
import com.example.musicplayer.R
import com.example.musicplayer.component.player.utils.MediaPlayerStatus
import com.example.musicplayer.component.player.utils.UIEvent

@Composable
internal fun MediaScreenPlayer(
    id: Int,
    vm: MediaViewModel = hiltViewModel(),
    startService: () -> Unit,
) {

    val uiStatePlayer by vm.uiStatePlayer.collectAsState()

    LaunchedEffect(Unit) {
        uiStatePlayer.loadData(id)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (uiStatePlayer.mediaPlayerStatus) {
            MediaPlayerStatus.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.size_30dp))
                    .align(Alignment.Center)
            )

            is MediaPlayerStatus.Ready -> {
                LaunchedEffect(true) {
                    startService()
                }
                MediaPlayerContent(
                    formatDuration = uiStatePlayer.formatDuration,
                    duration = uiStatePlayer.duration,
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
                if (isPlaying) R.drawable.ic_media_pause else R.drawable.ic_media_play
            },
            progressProvider = { Pair(progress, progressString) },
            onUiEvent = onUIEvent,
        )
    }
}