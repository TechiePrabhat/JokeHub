package com.example.jokehub.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGenerator {

     private const val BASE_URL="https://icanhazdadjoke.com/"





    fun getInstance():Retrofit{
        val logging=HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient=OkHttpClient.Builder()

        httpClient.addInterceptor(logging)

       /* val gson = GsonBuilder()
            .setLenient()
            .create()*/
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

}