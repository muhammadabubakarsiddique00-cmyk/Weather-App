package com.example.weatherapp

import com.google.android.gms.common.api.internal.ApiKey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("api/v4/search")
    fun getNews(
        @Query("q") city:String,
        @Query("lang") language:String,
        @Query("country") country:String,
        @Query("apikey") apiKey: String
    ) :Call<NewsResponse>
}