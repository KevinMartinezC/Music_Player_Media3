package com.example.data

import com.example.data.mapper.toSoundResult
import com.example.data.service.api.ApiSoundResult
import com.example.data.service.api.Images
import com.example.data.service.api.Previews
import junit.framework.TestCase.assertEquals
import org.junit.Test


class SoundResultMapperTest{
    @Test
    fun `toSoundResult maps properties correctly`() {
        // Arrange
        val apiSoundResult = ApiSoundResult(
            id = 123,
            name = "Test Name",
            username = "Test Username",
            previews = Previews("Test Preview Mp3"),
            images = Images("Test Waveform")
        )

        // Act
        val soundResult = apiSoundResult.toSoundResult()

        // Assert
        assertEquals(apiSoundResult.id, soundResult.id)
        assertEquals(apiSoundResult.name, soundResult.name)
        assertEquals(apiSoundResult.username, soundResult.username)
        assertEquals(apiSoundResult.previews.previewHqMp3, soundResult.previews.previewHqMp3)
        assertEquals(apiSoundResult.images.waveformM, soundResult.images.waveformM)
    }

}