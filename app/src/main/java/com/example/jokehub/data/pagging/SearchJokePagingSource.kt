package com.example.jokehub.data.pagging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.remote.services.JokeService

class SearchJokePagingSource(private val jokeService: JokeService, private val query:String) : PagingSource<Int, Result>(){


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        Log.d("555pmt","Pagginsource called")
        return try {
        val pagePosition = params.key ?: 1
        val response = jokeService.searchJoke(term = query, page = pagePosition).body()
        LoadResult.Page(
            data = response!!.results,
            prevKey = if (pagePosition==1) null else pagePosition-1,
            nextKey = if (pagePosition==response.total_pages)null else pagePosition+1
        )


    } catch (e: Exception) {
        LoadResult.Error(e)
    }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return  state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}