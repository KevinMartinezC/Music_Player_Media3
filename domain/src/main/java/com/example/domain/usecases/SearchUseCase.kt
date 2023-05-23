package com.example.domain.usecases

import com.example.domain.model.SoundResult
import com.example.domain.repository.GetSoundInfRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val sonRepository: GetSoundInfRepository
) {
    suspend fun execute(
        query: String,
        page: Int,
        pageSize: Int
        ): List<SoundResult>{
        return sonRepository.search(query, page, pageSize)
    }
}