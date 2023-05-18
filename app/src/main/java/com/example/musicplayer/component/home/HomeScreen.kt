package com.example.musicplayer.component.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import com.example.musicplayer.component.home.viewmodel.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {
    val searchQuery = remember { mutableStateOf("") }
    val searchResults by homeScreenViewModel.searchResults.collectAsState()

    Column(modifier = modifier) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search") },
            trailingIcon = { IconButton(onClick = { homeScreenViewModel.search(searchQuery.value) })
            { Icon(Icons.Default.Search, contentDescription = "Search Icon") } },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        searchResults?.let { results ->
            LazyColumn {
                items(results) { result ->
                    Text(text = result.name)  // Assuming SoundResult has a property 'title'
                }
            }
        }
    }}