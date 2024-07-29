package com.example.jokehub.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.jokehub.data.dto.JokeListData
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.local.JokeDatabase
import com.example.jokehub.data.pagging.FavJokePagingSource
import com.example.jokehub.data.pagging.JokeListPagingSource
import com.example.jokehub.data.pagging.SearchJokePagingSource
import com.example.jokehub.data.remote.services.JokeService
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.math.max

class JokeListRepository @Inject constructor(
    private val jokeService: JokeService,
    private val jokeDatabase: JokeDatabase
) {

    fun fetchJoke() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { JokeListPagingSource(jokeService) }).liveData


 /*   fun searchJoke(query: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { SearchJokePagingSource(jokeService, query) }
    ).liveData*/


    suspend fun addFavouriteJoke(joke: Result) {
        jokeDatabase.jokeDao().insertJoke(joke)
    }

    suspend fun deleteJoke(id:String){
        jokeDatabase.jokeDao().deleteJokeID(id)
    }
    // fetch Fav joke to map fav icon in joke list
    suspend fun getFavouriteJokes():List<Result>{
       return jokeDatabase.jokeDao().getAllJoke()
    }

   /* fun getFavJokes()=Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100)
    ){jokeDatabase.jokeDao().getJokes()}.liveData*/

}