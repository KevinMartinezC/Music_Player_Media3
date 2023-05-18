package com.example.musicplayer.component.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SoundResult
import com.example.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(){

    private val _searchResults = MutableStateFlow<List<SoundResult>?>(null)
    val searchResults: StateFlow<List<SoundResult>?> = _searchResults

    fun search(query: String) {
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
