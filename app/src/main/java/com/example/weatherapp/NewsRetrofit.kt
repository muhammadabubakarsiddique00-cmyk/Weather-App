package com.example.weatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gnews.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsApi: NewsApi =
        retrofit.create(NewsApi::class.java)

}