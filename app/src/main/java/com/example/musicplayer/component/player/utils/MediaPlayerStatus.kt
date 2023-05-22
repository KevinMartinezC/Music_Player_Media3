package com.example.musicplayer.component.player.utils

sealed class MediaPlayerStatus {
    object Initial : MediaPlayerStatus()
    object Ready : MediaPlayerStatus()
}