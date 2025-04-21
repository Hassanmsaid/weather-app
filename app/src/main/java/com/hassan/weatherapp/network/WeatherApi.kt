package com.hassan.weatherapp.network

import com.hassan.weatherapp.BuildConfig
import com.hassan.weatherapp.models.Weather
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface WeatherApi {
    @GET(value = "/data/2.5/forecast/daily")
    suspend fun getWeather(
        @Path("q") query: String,
        @Path("units") units: String = "metric",
        @Path("appid") appId: String = BuildConfig.WEATHER_API_KEY,
    ): Weather
}