package com.example.musicplayer.component.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.domain.SoundResult
import com.example.musicplayer.R
import com.example.musicplayer.component.home.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier, homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {

    val uiStateHome by homeScreenViewModel.uiStateHome.collectAsState()

    HomeScreenContent(
        search = uiStateHome.search,
        searchResultList = uiStateHome.searchResultList
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    search: (String) -> Unit,
    searchResultList: StateFlow<List<SoundResult>>,
) {
    val searchQuery = remember { mutableStateOf("") }
    val searchResults by searchResultList.collectAsState(initial = emptyList())
    val keyBoardControler = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE


    Column(modifier = Modifier) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text(stringResource(R.string.search)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                search(searchQuery.value)
                keyBoardControler?.hide()
            }),
            trailingIcon = {
                IconButton(onClick =
                {
                    scope.launch {
                        search(searchQuery.value)
                        keyBoardControler?.hide()
                    }

                })
                {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon)
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        searchResults.let { results ->
            if (isLandscape) {
                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                    items(results.size) { index ->
                        val result = results[index]
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Column {
                                Image(
                                    painter = rememberAsyncImagePainter(result.images.waveformM),
                                    contentDescription = "Image for ${result.name}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .aspectRatio(2f),
                                    contentScale = ContentScale.Crop,
                                )
                                Text(
                                    text = result.name,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .aspectRatio(3f),
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(results) { result ->
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Column {
                                Image(
                                    painter = rememberAsyncImagePainter(result.images.waveformM),
                                    contentDescription = "Image song ",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .height(180.dp)
                                        .fillMaxWidth(),
                                )
                                Text(
                                    text = result.name,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}