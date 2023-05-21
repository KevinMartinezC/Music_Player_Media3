package com.example.domain.usecases

import com.example.domain.SoundResult
import com.example.domain.repository.SongRepository
import javax.inject.Inject

class LoadSongUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(soundId: Int): SoundResult {
        return songRepository.getSoundResult(soundId)
    }
}
