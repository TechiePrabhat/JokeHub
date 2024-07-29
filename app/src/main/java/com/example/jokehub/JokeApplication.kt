package com.example.jokehub

import android.app.Application
import com.example.jokehub.data.local.JokeDatabase
import com.example.jokehub.data.remote.ServiceGenerator
import com.example.jokehub.data.remote.services.JokeService
import com.example.jokehub.data.repository.JokeListRepository
import com.example.jokehub.di.AppModule
import com.example.jokehub.di.DaggerJokeListComponent
import com.example.jokehub.di.JokeListComponent

class JokeApplication: Application() {

    lateinit var jokeListComponent: JokeListComponent


    override fun onCreate() {
        super.onCreate()
        initialize()
    }


    private fun initialize(){
        //Initialize the Dagger component
      jokeListComponent= DaggerJokeListComponent.builder().appModule(AppModule(this)).build()

    }
}