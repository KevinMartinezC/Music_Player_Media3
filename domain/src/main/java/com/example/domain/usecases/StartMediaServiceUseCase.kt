package com.example.domain.usecases

import com.example.domain.repository.MediaServiceHandlerRepository
import javax.inject.Inject

class StartMediaServiceUseCase @Inject constructor(
    private val mediaServiceHandlerRepository: MediaServiceHandlerRepository
) {
    fun execute() {
        mediaServiceHandlerRepository.startService()
    }
}
