package com.example.weatherapp

data class NewsResponse (

    val totalArticle : Int,
    val articles: List<NewsModel>
)