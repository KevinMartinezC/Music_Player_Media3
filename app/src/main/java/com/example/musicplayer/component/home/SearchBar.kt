package com.example.musicplayer.component.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.musicplayer.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    onSearch: (String) -> Unit,
) {
    val searchQuery = remember { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        label = { Text(stringResource(R.string.search)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(searchQuery.value)
            keyBoardController?.hide()
        }),
        trailingIcon = {
            IconButton(onClick = {
                scope.launch {
                    onSearch(searchQuery.value)
                    keyBoardController?.hide()
                }
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

}

