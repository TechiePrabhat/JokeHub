package com.example.jokehub.di

import android.app.Application
import com.example.jokehub.data.local.JokeDatabase
import com.example.jokehub.data.remote.ServiceGenerator
import com.example.jokehub.data.remote.services.JokeService
import dagger.Module
import dagger.Provides

@Module
class JokeListModule {

    @Provides
    fun provideJokeService():JokeService{
        return ServiceGenerator.getInstance().create(JokeService::class.java)
    }

    @Provides
    fun provideJokeDatabase(application: Application):JokeDatabase{
        return JokeDatabase.getDatabase(application)
    }
}