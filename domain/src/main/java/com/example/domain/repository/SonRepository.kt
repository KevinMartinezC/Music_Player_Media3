package com.example.domain.repository

import com.example.domain.SoundResult

interface SoundRepository {
    suspend fun search(query: String): List<SoundResult>
}
