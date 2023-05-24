package com.example.musicplayer.ui.component.player.utils

sealed class MediaPlayerStatus {
    object Initial : MediaPlayerStatus()
    object Ready : MediaPlayerStatus()
}