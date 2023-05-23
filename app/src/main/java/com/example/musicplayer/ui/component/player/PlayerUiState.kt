package com.example.musicplayer.ui.component.player

import com.example.musicplayer.ui.component.player.utils.MediaPlayerStatus
import com.example.musicplayer.ui.component.player.utils.UIEvent


data class PlayerUiState(
    val formatDuration: (Long) -> String = { "" },
    val duration: Long = 0L,
    var isPlaying: Boolean = false,
    val progress: Float = 0f,
    val progressString: String = "",
    val albumArtUrl: String = "",
    val onUIEvent: (UIEvent) -> Unit = {},
    val loadData: (Int) -> Unit,
    val mediaPlayerStatus: MediaPlayerStatus = MediaPlayerStatus.Initial,
)
