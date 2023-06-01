package com.example.musicplayer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.musicplayer.ui.component.home.SearchField
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenTest{

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

}
