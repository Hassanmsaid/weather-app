package com.hassan.weatherapp.models

data class City(
    val country: String = "",
    val coord: Coord,
    val timezone: Int = 0,
    val name: String = "",
    val id: Int = 0,
    val population: Int = 0
)