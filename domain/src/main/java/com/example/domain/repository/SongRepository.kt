package com.example.domain.repository

import com.example.domain.SoundResult

interface SongRepository {
    suspend fun getSoundResult(soundId: Int): SoundResult
}