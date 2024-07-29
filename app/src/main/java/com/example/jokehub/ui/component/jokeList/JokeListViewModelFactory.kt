package com.example.jokehub.ui.component.jokeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokehub.data.repository.JokeListRepository

class JokeListViewModelFactory(private val jokeListRepostiory:JokeListRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JokeListViewModel(jokeListRepostiory)as T
    }
}