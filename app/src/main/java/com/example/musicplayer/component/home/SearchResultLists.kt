package com.example.musicplayer.component.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.domain.SoundResult

@Composable
fun SearchResultsGrid(results: LazyPagingItems<SoundResult>, navController: NavHostController) {
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(results.itemCount) { index ->
            val animeItem = results[index] ?: return@items
            ResultCard(animeItem = animeItem, grid = true,  navController = navController,)
        }
    }
}

@Composable
fun SearchResultsColumn(results: LazyPagingItems<SoundResult>, navController: NavHostController) {
    LazyColumn {
        items(results.itemCount) { result ->
            val animeItem = results[result] ?: return@items
            ResultCard(animeItem = animeItem, grid = false,  navController = navController,)
        }
    }
}