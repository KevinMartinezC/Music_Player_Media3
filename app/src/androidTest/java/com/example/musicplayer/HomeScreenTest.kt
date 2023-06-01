package com.example.musicplayer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.model.Images
import com.example.domain.model.Previews
import com.example.domain.model.SoundResult
import com.example.musicplayer.ui.component.home.ResultCard
import com.example.musicplayer.ui.component.home.SearchField
import com.example.musicplayer.ui.component.home.SearchResultsColumn
import com.example.musicplayer.ui.component.home.SearchResultsGrid
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSearchInteraction() {
        val testSearchInput = "piano"
        var searchQuery = ""

        composeTestRule.setContent {
            SearchField { input -> searchQuery = input }
        }

        composeTestRule.onNodeWithTag("searchField")
            .performTextInput(testSearchInput)

        composeTestRule.onNodeWithTag("searchField")
            .performImeAction()

        assertEquals(testSearchInput, searchQuery)
    }

    @Test
    fun testSearchResultsGrid() {
        val soundResults = flowOf(
            PagingData.from(
                listOf(
                    SoundResult(
                        id = 123,
                        name = "Guitar Space",
                        username = "Test Username",
                        previews = Previews("Test"),
                        images = Images("Test Waveform")
                    ),
                    SoundResult(
                        id = 124,
                        name = "Guitar solo.wav",
                        username = "Test Username 2",
                        previews = Previews("Test 2"),
                        images = Images("Test Waveform 2")
                    )
                )
            )
        )

        composeTestRule.setContent {
            val soundResultItems = soundResults.collectAsLazyPagingItems()
            SearchResultsGrid(soundResultItems) { }
        }
        composeTestRule.onNodeWithText("Guitar Space").assertExists()
        composeTestRule.onNodeWithText("Guitar solo.wav").assertExists()
    }

    @Test
    fun testSearchResultsColumn() {
        val soundResults = flowOf(
            PagingData.from(
                listOf(
                    SoundResult(
                        id = 123,
                        name = "Guitar Space",
                        username = "Test Username",
                        previews = Previews("Test"),
                        images = Images("Test Waveform")
                    ),
                    SoundResult(
                        id = 124,
                        name = "Guitar solo.wav",
                        username = "Test Username 2",
                        previews = Previews("Test 2"),
                        images = Images("Test Waveform 2")
                    )
                )
            )
        )

        composeTestRule.setContent {
            val soundResultItems = soundResults.collectAsLazyPagingItems()
            SearchResultsColumn(soundResultItems) { }
        }
        composeTestRule.onNodeWithText("Guitar Space").assertExists()
        composeTestRule.onNodeWithText("Guitar solo.wav").assertExists()
    }

    @Test
    fun testItemClickInteraction() {
        val testResult = SoundResult(
            id = 123,
            name = "Guitar Space",
            username = "Test Username",
            previews = Previews("Test"),
            images = Images("Test Waveform")
        )
        val results = flowOf(
            PagingData.from(
                listOf(
                    testResult
                )
            )
        )

        var selectedItem: Int? = null
        composeTestRule.setContent {
            ResultCard(musicItem = testResult, grid = false) { selectedItem = it }
        }

        composeTestRule.onNodeWithTag("resultCard_${testResult.id}").performClick()

        assertEquals(testResult.id, selectedItem)
    }
}
