package com.example.data

import com.example.data.repositoryImpl.SongRepositoryImpl
import com.example.data.service.api.ApiSoundResult
import com.example.data.service.api.Images
import com.example.data.service.api.MyApiService
import com.example.data.service.api.Previews
import com.example.domain.model.SoundResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SongRepositoryImplTest {

    @Test
    fun `test getSoundResult() should return expected SoundResult`() = runTest {

        val apiService = mockk<MyApiService>()

        val songRepository = SongRepositoryImpl(apiService)

        val soundId = 123
        val apiSoundResult = ApiSoundResult(
            id = 123,
            name = "Test Name",
            username = "Test Username",
            previews = Previews("Test"),
            images = Images("Test Waveform")

        )

        coEvery { apiService.getSound(soundId) } returns apiSoundResult

        val expectedSoundResult = SoundResult(
            id = 123,
            name = "Test Name",
            username = "Test Username",
            previews = com.example.domain.model.Previews("Test"),
            images = com.example.domain.model.Images("Test Waveform")
        )

        val actualSoundResult = songRepository.getSoundResult(soundId)

        assertEquals(expectedSoundResult, actualSoundResult)

    }


}