package com.example.musicplayer.component.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.SoundResult
import com.example.musicplayer.component.home.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val uiStateHome by homeScreenViewModel.uiStateHome.collectAsState()

    val searchResults = homeScreenViewModel.searchResults.collectAsLazyPagingItems()

    HomeScreenContent(
        search = uiStateHome.search, searchResultList = searchResults, navController = navController
    )
}

@Composable
fun HomeScreenContent(
    search: (String) -> Unit,
    searchResultList: LazyPagingItems<SoundResult>,
    navController: NavHostController

) {

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = Modifier) {
        SearchField(search)

        searchResultList.let { results ->
            if (isLandscape) {
                SearchResultsGrid(results = results, navController = navController)
            } else {
                SearchResultsColumn(
                    results = results, navController = navController
                )
            }
        }
    }
}
