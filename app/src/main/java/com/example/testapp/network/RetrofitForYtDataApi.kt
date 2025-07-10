package com.example.testapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YoutubeApiInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: YoutubeApiService by lazy {
        retrofit.create(YoutubeApiService::class.java)
    }
}
