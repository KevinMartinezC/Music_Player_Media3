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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.Images
import com.example.domain.model.Previews
import com.example.domain.model.SoundResult
import com.example.musicplayer.R
import com.example.musicplayer.ui.component.home.viewmodel.HomeScreenViewModel
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import kotlinx.coroutines.flow.flowOf

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


@Preview
@Composable
fun HomeScreenContentLoadingPreview() {
    val fakeResults = flowOf(
        PagingData.from(
            listOf(
                SoundResult(
                    id = 1,
                    name = stringResource(R.string.name),
                    username = stringResource(R.string.user_name),
                    previews = Previews(previewHqMp3 = stringResource(R.string.music_mp3)),
                    images = Images(waveformM = stringResource(R.string.image))
                )
            )
        )
    ).collectAsLazyPagingItems()
    val navController = rememberNavController()
    MusicPlayerTheme {
        HomeScreenContent(
            search = {},
            searchResultList = fakeResults,
            navController = navController
        )
    }
}
