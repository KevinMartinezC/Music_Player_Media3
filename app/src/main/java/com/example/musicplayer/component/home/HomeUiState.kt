package com.example.musicplayer.component.home

import com.example.domain.SoundResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val search: (String) -> Unit = {},
    val searchResultList : StateFlow<List<SoundResult>> = MutableStateFlow(emptyList())
)
