package com.example.data.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.SoundResult
import com.example.domain.usecases.SearchUseCase

class SoundPagingSource(
    private val searchUseCase: SearchUseCase,
    private val query: String,
    private val pageSize: Int
) : PagingSource<Int, SoundResult>() {
    override fun getRefreshKey(state: PagingState<Int, SoundResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SoundResult> {
        val page = params.key ?: 1
        return try {
            val data = searchUseCase.execute(
                query = query,
                page = page,
                pageSize = pageSize
            )
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

