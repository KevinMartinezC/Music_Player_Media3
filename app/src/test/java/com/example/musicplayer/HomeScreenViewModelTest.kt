package com.example.musicplayer

import com.example.domain.usecases.SearchUseCase
import com.example.musicplayer.ui.component.home.viewmodel.HomeScreenViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test


class HomeScreenViewModelTest{
    private val searchUseCase: SearchUseCase = mockk()
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @Test
    fun `should update query when search is called`() = runTest {
        // Given
        val query = "Test"
        homeScreenViewModel = HomeScreenViewModel(searchUseCase)

        // When
        homeScreenViewModel.search(query)

        // Then
        val actual = homeScreenViewModel.query.first()
        assertEquals(query, actual)
    }
}