package com.hassan.weatherapp.models

data class Weather(
    val city: City,
    val cnt: Int = 0,
    val cod: String = "",
    val message: Double = 0.0,
    val list: List<WeatherObject>?
)