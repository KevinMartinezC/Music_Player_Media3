package com.example.domain.repository

import com.example.domain.SoundResult

interface GetSoundInfRepository {
    suspend fun search(
        query: String,
        page: Int,
        pageSize: Int
    ): List<SoundResult>
}
