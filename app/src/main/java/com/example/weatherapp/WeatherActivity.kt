package com.example.weatherapp

import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import android.widget.LinearLayout

class WeatherActivity : AppCompatActivity() {

    private lateinit var txtCity: TextView
    private lateinit var txtCountry: TextView
    private lateinit var txtTemperature: TextView
    private lateinit var txtCondition: TextView
    private lateinit var txtHumidity: TextView
    private lateinit var txtWindSpeed: TextView
    private lateinit var recyclerHourly: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        findViewById<LinearLayout>(R.id.navWeather).setOnClickListener {
            // Weather screen already open hai
        }

        findViewById<LinearLayout>(R.id.navNews).setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }
        Toast.makeText(
            this,
            "WeatherActivity started",
            Toast.LENGTH_SHORT
        ).show()

        txtCity = findViewById(R.id.txtCity)
        txtCountry = findViewById(R.id.txtCountry)
        txtTemperature = findViewById(R.id.txtTemperature)
        txtCondition = findViewById(R.id.txtCondition)
        txtHumidity = findViewById(R.id.txtHumidity)
        txtWindSpeed = findViewById(R.id.txtWindSpeed)
        recyclerHourly = findViewById(R.id.recyclerHourly)

        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        // Latitude/Longitude se city aur country
        getCityAndCountry(latitude, longitude)

        // RecyclerView ko horizontal banana
        recyclerHourly.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        // Open-Meteo API call
        weatherApi.getWeather(
            latitude,
            longitude
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {

                    val weather = response.body()

                    if (weather != null) {

                        // Current weather
                        txtTemperature.text =
                            "${weather.current.temperature_2m}°C"

                        txtHumidity.text =
                            "${weather.current.relative_humidity_2m}%"

                        txtWindSpeed.text =
                            "${weather.current.wind_speed_10m} km/h"

                        txtCondition.text =
                            getWeatherCondition(
                                weather.current.weather_code
                            )

                        // Hourly forecast
                        val hourlyList =
                            ArrayList<ForecastModel>()

                        for (i in 0 until 12) {

                            hourlyList.add(
                                ForecastModel(
                                    weather.hourly.time[i],
                                    weather.hourly.temperature_2m[i]
                                )
                            )
                        }

                        recyclerHourly.adapter =
                            ForecastAdapter(hourlyList)
                    }

                } else {

                    Toast.makeText(
                        this@WeatherActivity,
                        "Weather data not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {

                Toast.makeText(
                    this@WeatherActivity,
                    "Internet error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // City aur Country find karna
    private fun getCityAndCountry(
        latitude: Double,
        longitude: Double
    ) {

        val geocoder = Geocoder(this)

        try {

            val addresses =
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )

            if (!addresses.isNullOrEmpty()) {

                val address = addresses[0]

                txtCity.text =
                    address.locality ?: "Unknown City"

                txtCountry.text =
                    address.countryName ?: "Unknown Country"
            }

        } catch (e: Exception) {

            txtCity.text = "Unknown City"
            txtCountry.text = "Unknown Country"
        }
    }

    // Weather code ko readable condition mein convert karna
    private fun getWeatherCondition(code: Int): String {

        return when (code) {

            0 -> "Clear Sky"

            1, 2, 3 -> "Cloudy"

            45, 48 -> "Foggy"

            51, 53, 55,
            56, 57 -> "Drizzle"

            61, 63, 65,
            66, 67 -> "Rain"

            71, 73, 75,
            77 -> "Snow"

            80, 81, 82 -> "Rain Showers"

            85, 86 -> "Snow Showers"

            95, 96, 99 -> "Thunderstorm"

            else -> "Unknown"
        }
    }
}