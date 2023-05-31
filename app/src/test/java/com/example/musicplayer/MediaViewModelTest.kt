package com.example.musicplayer


import com.example.data.service.media.MediaServiceHandler
import com.example.data.service.media.utils.MediaState
import com.example.data.service.media.utils.PlayerEvent
import com.example.domain.usecases.LoadSongUseCase
import com.example.musicplayer.ui.component.player.utils.UIEvent
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
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

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        mediaStateFlow.value = MediaState.Playing(true)

        assertEquals(true, viewModel.uiStatePlayer.value.isPlaying)
    }

    @Test
    fun collectMediaState_emitsMediaStateReady_changesUiStatePlayerDuration() = runTest {

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        val duration = 100L
        mediaStateFlow.value = MediaState.Ready(duration)

        assertEquals(duration, viewModel.uiStatePlayer.value.duration)
    }

    @Test
    fun onCleared_callsOnPlayerEventWithStop() = runTest{

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow
        coEvery { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop) } just Runs

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        viewModel.onCleared()

        coVerify { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Stop) }
    }

    @Test
    fun onUIEvent_whenBackward_callsOnPlayerEventWithBackward() = runTest {

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow
        coEvery { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Backward) } just Runs

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        viewModel.onUIEvent(UIEvent.Backward)

        coVerify { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Backward) }

    }

    @Test
    fun onUIEvent_whenForward_callsOnPlayerEventWithForward() = runTest {

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow
        coEvery { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Forward) } just Runs

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        viewModel.onUIEvent(UIEvent.Forward)

        coVerify { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.Forward) }

    }

    @Test
    fun onUIEvent_whenPlayPause_callsOnPlayerEventWithPlayPause() = runTest {

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow
        coEvery { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause) } just Runs

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        viewModel.onUIEvent(UIEvent.PlayPause)

        coVerify { mockMediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause) }

    }

}