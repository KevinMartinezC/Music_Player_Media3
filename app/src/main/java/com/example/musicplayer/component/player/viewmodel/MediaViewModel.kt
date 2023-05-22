package com.example.musicplayer.component.player.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.data.service.media.PlayerEvent
import com.example.data.service.media.SimpleMediaServiceHandler
import com.example.data.service.media.SimpleMediaState
import com.example.domain.usecases.LoadSongUseCase
import com.example.musicplayer.component.player.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val simpleMediaServiceHandler: SimpleMediaServiceHandler,
    private val loadSongUseCase: LoadSongUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _uiStatePlayer = MutableStateFlow(
        PlayerUiState(
            formatDuration = ::formatDuration,
            duration = 0L,
            isPlaying = false,
            progress = 0f,
            progressString = "00:00",
            onUIEvent = ::onUIEvent
        )
    )
    val uiStatePlayer = _uiStatePlayer.asStateFlow()

    init {
        collectMediaState()
    }

    private fun collectMediaState() {
        viewModelScope.launch {
            simpleMediaServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
                    is SimpleMediaState.Playing -> _uiStatePlayer.value = _uiStatePlayer.value.copy(isPlaying = mediaState.isPlaying)
                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is SimpleMediaState.Ready -> {
                        _uiStatePlayer.value = _uiStatePlayer.value.copy(duration = mediaState.duration)
                        _uiState.value = UIState.Ready
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
    }

    private fun onUIEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                UIEvent.Backward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
                UIEvent.Forward -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
                UIEvent.PlayPause -> simpleMediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            }
        }
    }

    private fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        val calculatedProgress =
            if (currentProgress > 0) (currentProgress.toFloat() / _uiStatePlayer.value.duration) else 0f
        val calculatedProgressString = formatDuration(currentProgress)
        _uiStatePlayer.value = _uiStatePlayer.value.copy(
            progress = calculatedProgress,
            progressString = calculatedProgressString
        )
    }


    fun loadData(id: Int) = viewModelScope.launch {
        runCatching {
            val soundResult = loadSongUseCase(id)
            val mediaItem = MediaItem.Builder()
                .setUri(soundResult.previews.previewHqMp3)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                        .setArtworkUri(Uri.parse(soundResult.images.waveformM))
                        .setAlbumTitle(soundResult.name)
                        .setDisplayTitle(soundResult.username)
                        .build()
                )
                .build()

            simpleMediaServiceHandler.addMediaItem(mediaItem)
        }.onFailure { e -> Log.d("Error", "${e.message}") }
    }
}

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}