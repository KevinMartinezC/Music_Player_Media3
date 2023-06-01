package com.example.musicplayer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicplayer.ui.component.player.MediaPlayerContent
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
@RunWith(AndroidJUnit4::class)
class MediaScreenPlayerKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun formattedDuration() {
        // given
        val testDuration = 120000L
        val testFormatDuration: (Long) -> String = { millis ->
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
            String.format("%02d:%02d", minutes, seconds)
        }

        composeTestRule.setContent {
            MediaPlayerContent(
                formatDuration = testFormatDuration,
                duration = testDuration,
                isPlaying = false,
                progress = 0f,
                progressString = "",
                albumArtUrl = ""
            ) { }
        }

        composeTestRule.onNodeWithTag("durationText").assertExists()
        composeTestRule.onNodeWithTag("durationText").assertTextEquals("02:00")
    }

    @Test
    fun mediaPlayerContent_displays_correct_pause_icon_when_playing() {
        composeTestRule.setContent {
            MediaPlayerContent(
                formatDuration = { "" },
                duration = 0L,
                isPlaying = true,
                progress = 0f,
                progressString = "",
                albumArtUrl = "",
                onUIEvent = {  }
            )
        }

        composeTestRule.onNodeWithTag("PauseIcon").assertExists()
    }

    @Test
    fun mediaPlayerContent_displays_correct_play_icon_when_paused() {
        composeTestRule.setContent {
            MediaPlayerContent(
                formatDuration = { "" },
                duration = 0L,
                isPlaying = false,
                progress = 0f,
                progressString = "",
                albumArtUrl = "",
                onUIEvent = {  }
            )
        }

        composeTestRule.onNodeWithTag("PlayIcon").assertExists()
    }
}