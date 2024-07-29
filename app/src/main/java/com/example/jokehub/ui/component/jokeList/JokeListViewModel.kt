package com.example.jokehub.ui.component.jokeList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.repository.JokeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeListViewModel @Inject constructor (private val jokeListRepository: JokeListRepository) : ViewModel() {

    lateinit var mFabJokes: List<Result>

    init {
        getFabJokes()
    }


    val jokes: LiveData<PagingData<Result>> =
        jokeListRepository.fetchJoke().cachedIn(viewModelScope)

    val updatedJoke = jokes.map { it -> it.map { result -> updateResult(result) } }

    fun getFabJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            mFabJokes = jokeListRepository.getFavouriteJokes()
        }
    }

    fun updateResult(data: Result): Result {
        if (mFabJokes.any{it.id==data.id}){  data.isFav = 1}
        return data
    }


    fun addFavoriteJoke(joke: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            jokeListRepository.addFavouriteJoke(joke)
        }

    }


    fun deleteFavourite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jokeListRepository.deleteJoke(id)
        }
    }


}