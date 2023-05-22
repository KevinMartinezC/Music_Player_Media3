package com.example.data.repositoryImpl

import com.example.data.service.api.MyApiService
import com.example.data.mapper.toSoundResult
import com.example.domain.model.SoundResult
import com.example.domain.repository.GetSoundInfRepository
import javax.inject.Inject

class GetSoundInfRepositoryImpl @Inject constructor(private val apiService: MyApiService) : GetSoundInfRepository {
    override suspend fun search(
        query: String,
        page: Int,
        pageSize: Int
    ): List<SoundResult> {
        val apiResponse = apiService.search(query, page.toString(), pageSize.toString())
        return apiResponse.results.map { it.toSoundResult() }
    }
}
