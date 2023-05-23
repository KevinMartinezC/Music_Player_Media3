package com.example.musicplayer.ui.component.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.SoundResult
import com.example.musicplayer.R
import com.example.musicplayer.ui.component.home.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiStateHome by homeScreenViewModel.uiStateHome.collectAsState()
    val searchResults = homeScreenViewModel.searchResults.collectAsLazyPagingItems()

    HomeScreenContent(
        modifier = modifier,
        search = uiStateHome.search,
        searchResultList = searchResults,
        navController = navController
    )
}

@Composable
fun HomeScreenContent(
    search: (String) -> Unit,
    searchResultList: LazyPagingItems<SoundResult>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = modifier.fillMaxSize()) {
        SearchField(search)

        when {
            searchResultList.loadState.refresh is LoadState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            searchResultList.itemCount == 0 -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        stringResource(R.string.no_results_found),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            else -> {
                if (isLandscape) {
                    SearchResultsGrid(results = searchResultList, navController = navController)
                } else {
                    SearchResultsColumn(
                        results = searchResultList, navController = navController
                    )
                }
            }
        }
    }
}


