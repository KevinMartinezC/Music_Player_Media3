package com.example.musicplayer.component.player

import com.example.musicplayer.component.player.viewmodel.UIEvent

data class PlayerUiState(
    val formatDuration: (Long) -> String = { "" },
    val duration: Long = 0L,
    var isPlaying: Boolean = false,
    val progress: Float = 0f,
    val progressString: String = "",
    val onUIEvent: (UIEvent) -> Unit = {}

)
