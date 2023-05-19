package com.example.musicplayer.component.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.SoundPagingSource
import com.example.domain.SoundResult
import com.example.domain.usecases.SearchUseCase
import com.example.musicplayer.component.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _uiStateHome = MutableStateFlow(HomeUiState(search = this::search))

    val uiStateHome = _uiStateHome.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<SoundResult>> = _query.flatMapLatest { query ->
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { SoundPagingSource(searchUseCase, query, 20) }
        ).flow
    }.cachedIn(viewModelScope)

    private fun search(query: String) {
        _query.value = query
    }
}