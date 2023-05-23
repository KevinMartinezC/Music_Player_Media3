package com.example.musicplayer.ui.component.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.domain.model.SoundResult

private const val GRID_CELLS_SIZE = 4
@Composable
fun SearchResultsGrid(results: LazyPagingItems<SoundResult>, navController: NavHostController) {
    LazyVerticalGrid(columns = GridCells.Fixed(GRID_CELLS_SIZE)) {
        items(results.itemCount) { index ->
            val animeItem = results[index] ?: return@items
            ResultCard(musicItem = animeItem, grid = true,  navController = navController)
        }
    }
}

@Composable
fun SearchResultsColumn(results: LazyPagingItems<SoundResult>, navController: NavHostController) {
    LazyColumn {
        items(results.itemCount) { result ->
            val animeItem = results[result] ?: return@items
            ResultCard(musicItem = animeItem, grid = false,  navController = navController)
        }
    }
}