package com.hassan.weatherapp.models

data class WeatherObject(
    val sunrise: Int = 0,
    val temp: Temp,
    val deg: Int = 0,
    val pressure: Int = 0,
    val clouds: Int = 0,
    val feelsLike: FeelsLike,
    val speed: Double = 0.0,
    val dt: Int = 0,
    val pop: Double = 0.0,
    val sunset: Int = 0,
    val weather: List<WeatherItem>?,
    val humidity: Int = 0,
    val gust: Double = 0.0
)