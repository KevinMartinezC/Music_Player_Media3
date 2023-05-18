package com.example.domain.usecases

import com.example.domain.SoundResult
import com.example.domain.repository.SoundRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val sonRepository: SoundRepository) {
    suspend fun execute(query: String): List<SoundResult>{
        return sonRepository.search(query)
    }
}