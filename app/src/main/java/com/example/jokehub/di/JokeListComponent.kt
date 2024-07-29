package com.example.jokehub.di

import com.example.jokehub.ui.component.favouriteJoke.FavouriteJokesActivity
import com.example.jokehub.ui.component.jokeList.JokeListingActivity
import com.example.jokehub.ui.component.searchJoke.SearchJokeActivity
import dagger.Component

@Component(modules = [AppModule::class,JokeListModule::class])
interface JokeListComponent {
    fun inject(jokeListingActivity: JokeListingActivity)
    fun inject(favouriteJokesActivity: FavouriteJokesActivity)
    fun inject(searchJokeActivity: SearchJokeActivity)
}