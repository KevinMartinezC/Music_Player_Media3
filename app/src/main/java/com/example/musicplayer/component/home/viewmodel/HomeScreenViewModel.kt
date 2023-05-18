package com.example.musicplayer.component.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(){

    fun search(query: String) {
        viewModelScope.launch {
            try {
                Log.wtf("SoundViewModel", "Starting search for query: $query")
                val result = searchUseCase.execute(query)
                Log.wtf("SoundViewModel", "Results: $result")
            } catch (e: Exception) {
                Log.wtf("SoundViewModel", "API Error: ${e.message}")
            }
        }
    }

}