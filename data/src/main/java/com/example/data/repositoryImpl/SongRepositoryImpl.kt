package com.example.data.repositoryImpl

import com.example.domain.repository.SongRepository
import javax.inject.Inject
import com.example.data.service.api.MyApiService
import com.example.data.mapper.toSoundResult
import com.example.domain.model.SoundResult


class SongRepositoryImpl @Inject constructor(
    private val apiService: MyApiService
) : SongRepository {
    override suspend fun getSoundResult(soundId: Int): SoundResult {
        val apiResult = apiService.getSound(soundId)
        return apiResult.toSoundResult()
    }
}