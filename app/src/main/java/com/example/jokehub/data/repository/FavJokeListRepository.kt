package com.example.jokehub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.local.JokeDatabase
import com.example.jokehub.data.remote.services.JokeService
import javax.inject.Inject

class FavJokeListRepository @Inject constructor(private val jokeService: JokeService,
                            private val jokeDatabase: JokeDatabase) {


    fun getFavJokes()= Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100)
    ){jokeDatabase.jokeDao().getJokes()}.liveData


    suspend fun addFavouriteJoke(joke: Result) {
        jokeDatabase.jokeDao().insertJoke(joke)
    }

    suspend fun deleteJoke(id:String){
        jokeDatabase.jokeDao().deleteJokeID(id)
    }
}