package com.example.data.repositoryImpl

import com.example.data.MyApiService
import com.example.data.mapper.toSoundResult
import com.example.domain.SoundResult
import com.example.domain.repository.SoundRepository
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor(private val apiService: MyApiService) : SoundRepository {
    override suspend fun search(query: String): List<SoundResult> {
        val apiResponse = apiService.search(query)
        return apiResponse.results.map { it.toSoundResult() }
    }
}
