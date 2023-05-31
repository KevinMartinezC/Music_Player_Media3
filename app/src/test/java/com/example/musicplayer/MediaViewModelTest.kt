package com.example.musicplayer


import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.data.service.media.MediaServiceHandler
import com.example.data.service.media.utils.MediaState
import com.example.data.service.media.utils.PlayerEvent
import com.example.domain.model.SoundResult
import com.example.domain.usecases.LoadSongUseCase
import com.example.musicplayer.ui.component.player.utils.UIEvent
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel.Companion.DEFAULT_PROGRESS_PERCENTAGE
import com.example.musicplayer.ui.component.player.viewmodel.MediaViewModel.Companion.DEFAULT_PROGRESS_VALUE
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MediaViewModelTest {

    private lateinit var mockMediaServiceHandler: MediaServiceHandler
    private lateinit var mockLoadSongUseCase: LoadSongUseCase
    private lateinit var viewModel: MediaViewModel
    private lateinit var mediaStateFlow: MutableStateFlow<MediaState>

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        mockMediaServiceHandler = mockk(relaxed = true)
        mockLoadSongUseCase = mockk(relaxed = true)
        mediaStateFlow = MutableStateFlow(MediaState.Initial)
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow
        viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)
    }

    @After
    fun tearDown() {
        clearMocks(mockMediaServiceHandler, mockLoadSongUseCase)
    }

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
    fun onCleared_callsOnPlayerEventWithStop() = runTest {

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

    @Test
    fun formatDuration_formatsDurationProperly() {
        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)
        val durationInMilliseconds = 90000L

        val formattedDuration = viewModel.formatDuration(durationInMilliseconds)

        assertEquals("01:30", formattedDuration)
    }

    @Test
    fun calculateProgressValues_updatesUiStatePlayerCorrectly() = runTest {

        val duration = 20000L
        val currentProgress = 10000L

        val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Ready(duration))
        coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

        val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

        val expectedProgress = currentProgress.toFloat() / duration
        val expectedProgressString = viewModel.formatDuration(currentProgress)

        viewModel.calculateProgressValues(currentProgress)

        assertEquals(expectedProgress, viewModel.uiStatePlayer.value.progress)
        assertEquals(expectedProgressString, viewModel.uiStatePlayer.value.progressString)
    }

    @Test
    fun calculateProgressValues_whenProgressIsDefault_updatesUiStatePlayerWithDefaultPercentage() =
        runTest {

            val duration = 20000L

            val mediaStateFlow = MutableStateFlow<MediaState>(MediaState.Ready(duration))
            coEvery { mockMediaServiceHandler.mediaState } returns mediaStateFlow

            val viewModel = MediaViewModel(mockMediaServiceHandler, mockLoadSongUseCase)

            val expectedProgress = DEFAULT_PROGRESS_PERCENTAGE
            val expectedProgressString = viewModel.formatDuration(DEFAULT_PROGRESS_VALUE)

            viewModel.calculateProgressValues(DEFAULT_PROGRESS_VALUE)

            assertEquals(expectedProgress, viewModel.uiStatePlayer.value.progress)
            assertEquals(expectedProgressString, viewModel.uiStatePlayer.value.progressString)
        }

    @Test
    fun loadData_callsLoadSongUseCaseAndMediaServiceHandler() = runTest {
        // Given
        val id = 123
        val soundResult = mockk<SoundResult>(relaxed = true)
        val mediaItem = MediaItem.Builder()
            .setUri(soundResult.previews.previewHqMp3)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    .setArtworkUri(Uri.parse(soundResult.images.waveformM))
                    .setAlbumTitle(soundResult.name)
                    .setDisplayTitle(soundResult.username)
                    .build()
            )
            .build()

        coEvery { mockLoadSongUseCase(id) } returns soundResult
        coEvery { mockMediaServiceHandler.addMediaItem(mediaItem) } just Runs

        // When
        viewModel.loadData(id)

        // Then
        coVerify { mockLoadSongUseCase(id) }
        coVerify { mockMediaServiceHandler.addMediaItem(mediaItem) }
        assertEquals(soundResult.images.waveformM, viewModel.uiStatePlayer.value.albumArtUrl)
    }
}