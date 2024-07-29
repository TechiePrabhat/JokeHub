package com.example.jokehub.data.pagging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.local.JokeDao

class FavJokePagingSource(private val jokeDao: JokeDao):PagingSource<Int,Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

       return try {
            val pageNo=params.key?:0
            val response= jokeDao.getFabJokes(params.loadSize,pageNo*params.loadSize)
           Log.d("prabhat555",response.get(0).joke+"777777777")
            LoadResult.Page(
                data = response,
                prevKey = if (pageNo==0)null else pageNo-1,
                nextKey = if (response.isEmpty())null else pageNo+1
            )
        }catch(e: Exception){
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