package com.example.musicplayer.ui.component.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.model.Images
import com.example.domain.model.Previews
import com.example.domain.model.SoundResult
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import kotlinx.coroutines.flow.flowOf

private const val GRID_CELLS_SIZE = 4

@Composable
fun SearchResultsGrid(results: LazyPagingItems<SoundResult>,onItemSelected: (Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(GRID_CELLS_SIZE)) {
        items(results.itemCount) { index ->
            val animeItem = results[index] ?: return@items
            ResultCard(musicItem = animeItem, grid = true,onItemSelected = onItemSelected )
        }
    }
}

@Composable
fun SearchResultsColumn(results: LazyPagingItems<SoundResult>,onItemSelected: (Int) -> Unit ) {
    LazyColumn {
        items(results.itemCount) { result ->
            val animeItem = results[result] ?: return@items
            ResultCard(musicItem = animeItem, grid = false,onItemSelected = onItemSelected )
        }
    }
}

@Composable
@Preview
private fun SearchResultsGridPreview() {
    val fakeResults = flowOf(
        PagingData.from(
            listOf(
                SoundResult(
                    id = 1,
                    name = stringResource(R.string.name),
                    username = stringResource(R.string.user_name),
                    previews = Previews(previewHqMp3 = stringResource(R.string.music_mp3)),
                    images = Images(waveformM = stringResource(R.string.image))
                ),
                SoundResult(
                    id = 1,
                    name = stringResource(R.string.name),
                    username = stringResource(R.string.user_name),
                    previews = Previews(previewHqMp3 = stringResource(R.string.music_mp3)),
                    images = Images(waveformM = stringResource(R.string.image))
                ),
                SoundResult(
                    id = 1,
                    name = stringResource(R.string.name),
                    username = stringResource(R.string.user_name),
                    previews = Previews(previewHqMp3 = stringResource(R.string.music_mp3)),
                    images = Images(waveformM = stringResource(R.string.image))
                ),
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

    MusicPlayerTheme {
        SearchResultsGrid(results = fakeResults, onItemSelected = {} )
    }
}

@Composable
@Preview
private fun SearchResultsColumnPreview() {
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

    SearchResultsColumn(results = fakeResults, onItemSelected = {})
}