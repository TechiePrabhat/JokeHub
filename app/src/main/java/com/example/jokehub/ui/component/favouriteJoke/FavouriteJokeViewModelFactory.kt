package com.example.jokehub.ui.component.favouriteJoke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokehub.data.repository.FavJokeListRepository
import com.example.jokehub.data.repository.JokeListRepository

class FavouriteJokeViewModelFactory(private val jokeListRepository: FavJokeListRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteJokeViewModel(jokeListRepository) as T
    }
}