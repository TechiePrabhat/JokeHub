package com.example.jokehub.ui.component.searchJoke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jokehub.data.repository.JokeListRepository
import com.example.jokehub.data.repository.SearchJokeRepository

class SearchJokeViewModelFactory(private val searchJokeRepository: SearchJokeRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(searchJokeRepository) as T
    }

}