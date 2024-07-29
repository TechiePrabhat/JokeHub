package com.example.jokehub.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.remote.services.JokeService

class JokeListPagingSource(private val jokeServie: JokeService) : PagingSource<Int, Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val pagePosition = params.key ?: 1
            val response = jokeServie.getJokeList(pagePosition).body()
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