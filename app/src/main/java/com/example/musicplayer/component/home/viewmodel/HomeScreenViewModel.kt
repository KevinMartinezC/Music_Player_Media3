package com.example.musicplayer.component.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SoundResult
import com.example.domain.usecases.SearchUseCase
import com.example.musicplayer.component.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(){

    private val _searchResults = MutableStateFlow<List<SoundResult>>(emptyList())

    private val _uiStateHome= MutableStateFlow(
        HomeUiState(
            search =  this::search,
            searchResultList = _searchResults
        )
    )

    val uiStateHome = _uiStateHome.asStateFlow()

    private fun search(query: String) {
        viewModelScope.launch {
            try {
                val result = searchUseCase.execute(query)
                _searchResults.emit(result)
            } catch (e: Exception) {
                Log.wtf("SoundViewModel", "API Error: ${e.message}")
            }
        }
    }
}
