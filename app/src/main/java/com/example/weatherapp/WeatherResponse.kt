package com.example.weatherapp

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val relative_humidity_2m: Int,
    val wind_speed_10m: Double,
    val weather_code: Int
)

data class HourlyWeather(
    val time: List<String>,
    val temperature_2m: List<Double>
)