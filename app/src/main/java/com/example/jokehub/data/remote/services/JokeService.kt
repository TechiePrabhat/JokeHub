package com.example.jokehub.data.remote.services

import com.example.jokehub.data.dto.JokeListData
import com.example.jokehub.data.dto.Result
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface JokeService {

    @Headers(
        "Accept:application/json"
    )
    @GET("/search")
    suspend fun getJokeList(@Query("page") page: Int): retrofit2.Response<JokeListData>

    @Headers("Accept:application/json")
    @GET("/search")
    suspend fun searchJoke(
        @Query("term") term: String,
        @Query("page") page: Int
    ): retrofit2.Response<JokeListData>


}