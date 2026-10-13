package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private lateinit var recyclerNews: RecyclerView

    private val apiKey = "b862a99d7eb96542546dadd0379d3e2c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        recyclerNews = findViewById(R.id.recyclerNews)
        recyclerNews.layoutManager = LinearLayoutManager(this)

        loadNews()

        findViewById<LinearLayout>(R.id.navWeather).setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)

            intent.putExtra(
                "latitude",
                getIntent().getDoubleExtra("latitude", 0.0)
            )

            intent.putExtra(
                "longitude",
                getIntent().getDoubleExtra("longitude", 0.0)
            )

            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.navNews).setOnClickListener {
            // News screen already open hai
        }
    }

    private fun loadNews() {

        val call = NewsRetrofit.newsApi.getNews(
            city = "Pakistan",
            language = "en",
            country = "pk",
            apiKey = apiKey
        )

        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val newsList = response.body()?.articles

                    if (newsList != null) {
                        recyclerNews.adapter = NewsAdapter(newsList)
                    }
                } else {
                    Toast.makeText(
                        this@NewsActivity,
                        "News load nahi hui: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    this@NewsActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}