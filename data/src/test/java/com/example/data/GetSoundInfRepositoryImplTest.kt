package com.example.data

import com.example.data.repositoryImpl.GetSoundInfRepositoryImpl
import com.example.data.service.api.ApiResponse
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


class GetSoundInfRepositoryImplTest{
    @Test
    fun `test search() with non-empty query should return expected SoundResult list`() = runTest {

        val apiService = mockk<MyApiService>()

        val repository = GetSoundInfRepositoryImpl(apiService)

        val query = "music"
        val page = 1
        val pageSize = 10
        val apiResponse = ApiResponse(
            count = 2,
            next = "",
            previous = "",
            results = listOf(
                ApiSoundResult(
                    id = 123,
                    name = "Test Name",
                    username = "Test Username",
                    previews = Previews("Test"),
                    images = Images("Test Waveform")
                )
                , ApiSoundResult(
                    id = 124,
                    name = "Test Name",
                    username = "Test Username",
                    previews = Previews("Test"),
                    images = Images("Test Waveform")
                ))
        )

        coEvery { apiService.search(query, page.toString(), pageSize.toString()) } returns apiResponse

        val expectedSoundResults = listOf(
            SoundResult(
                id = 123,
                name = "Test Name",
                username = "Test Username",
                previews = com.example.domain.model.Previews("Test"),
                images = com.example.domain.model.Images("Test Waveform")
            ),
            SoundResult( id = 124,
                name = "Test Name",
                username = "Test Username",
                previews = com.example.domain.model.Previews("Test"),
                images = com.example.domain.model.Images("Test Waveform"))
        )

        val actualSoundResults = repository.search(query, page, pageSize)

        assertEquals(expectedSoundResults, actualSoundResults)
    }

    @Test
    fun `test search() with empty query should return an empty SoundResult list`() = runTest {
        val apiService = mockk<MyApiService>()

        val repository = GetSoundInfRepositoryImpl(apiService)

        val query = ""
        val page = 1
        val pageSize = 10

        val actualSoundResults = repository.search(query, page, pageSize)

        assertEquals(emptyList<SoundResult>(), actualSoundResults)
    }
}