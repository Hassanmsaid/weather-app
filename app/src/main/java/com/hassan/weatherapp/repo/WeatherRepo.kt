package com.hassan.weatherapp.repo

import android.util.Log
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityName: String): DataOrException<WeatherResponse, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityName)
        } catch (e: Exception) {
            Log.e("Weather repo:", "getWeather: ", e)
            return DataOrException(e = e)
        }
        Log.i("Weather repo:", "getWeather: ${response.city}")
        return DataOrException(data = response)
    }
}