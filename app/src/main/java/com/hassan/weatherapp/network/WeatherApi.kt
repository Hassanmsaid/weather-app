package com.hassan.weatherapp.network

import com.hassan.weatherapp.BuildConfig
import com.hassan.weatherapp.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(value = "/data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = BuildConfig.WEATHER_API_KEY,
    ): WeatherResponse
}