package com.example.jokehub.ui.component.searchJoke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.repository.SearchJokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchJokeRepository: SearchJokeRepository) : ViewModel() {






        fun searchJoke(query:String): LiveData<PagingData<Result>> {
            Log.d("555pmt","viewModel called")
             var Data = searchJokeRepository.searchJoke(query).cachedIn(viewModelScope)
            return Data
        }


    fun addFavoriteJoke(joke: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            searchJokeRepository.addFavouriteJoke(joke)
        }

    }

    fun deleteFavourite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchJokeRepository.deleteJoke(id)
        }
    }



}