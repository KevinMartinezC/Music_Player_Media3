package com.example.musicplayer.ui.component.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel
import com.example.musicplayer.R
import com.example.musicplayer.ui.component.player.utils.MediaPlayerStatus
import com.example.musicplayer.ui.component.player.utils.UIEvent

@Composable
internal fun MediaScreenPlayer(
    id: Int,
    vm: MediaViewModel = hiltViewModel(),
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
                MediaPlayerContent(
                    formatDuration = uiStatePlayer.formatDuration,
                    duration = uiStatePlayer.duration,
                    isPlaying = uiStatePlayer.isPlaying,
                    progress = uiStatePlayer.progress,
                    progressString = uiStatePlayer.progressString,
                    onUIEvent = uiStatePlayer.onUIEvent,
                    albumArtUrl = uiStatePlayer.albumArtUrl
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
    albumArtUrl: String,
    onUIEvent: (UIEvent) -> Unit
) {

val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),

        ) {
        Image(
            painter = rememberAsyncImagePainter(albumArtUrl),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(4f)
                .align(Alignment.CenterHorizontally)
        )
        MediaPlayerUI(
            durationString = formatDuration(duration),
            playResourceProvider = {
                if (isPlaying) R.drawable.ic_media_pause else R.drawable.ic_media_play
            },
            progressProvider = { Pair(progress, progressString) },
            onUiEvent = onUIEvent,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_60dp)))

    }
}