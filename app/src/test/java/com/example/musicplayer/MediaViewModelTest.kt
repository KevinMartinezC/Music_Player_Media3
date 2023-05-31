package com.example.musicplayer


import com.example.data.service.media.MediaServiceHandler
import com.example.data.service.media.utils.MediaState
import com.example.domain.usecases.LoadSongUseCase
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class MediaViewModelTest {

    private val mockMediaServiceHandler = mockk<MediaServiceHandler>()
    private val mockLoadSongUseCase = mockk<LoadSongUseCase>()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Test
    fun collectMediaState_emitsMediaState_changesUiStatePlayer() = runTest {
        // Arrange
        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        // Act
        mediaStateFlow.value = MediaState.Playing(true)

        // Assert
        assertEquals(true, viewModel.uiStatePlayer.value.isPlaying)
    }

    @Test
    fun collectMediaState_emitsMediaStateReady_changesUiStatePlayerDuration() = runTest {
        // Arrange
        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        // Act
        val duration = 100L
        mediaStateFlow.value = MediaState.Ready(duration)

        // Assert
        assertEquals(duration, viewModel.uiStatePlayer.value.duration)
    }

}