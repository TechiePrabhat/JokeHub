package com.example.jokehub.ui.component.favouriteJoke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.repository.FavJokeListRepository
import com.example.jokehub.data.repository.JokeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteJokeViewModel @Inject constructor(private val repository: FavJokeListRepository):ViewModel() {



  val jokes =repository.getFavJokes().cachedIn(viewModelScope)

  fun deleteFavourite(id:String){
    viewModelScope.launch (Dispatchers.IO){
      repository.deleteJoke(id)
    }
  }
}