package com.hassan.weatherapp.ui.screens.main

import androidx.lifecycle.ViewModel
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {
    suspend fun getWeatherData(city: String): DataOrException<WeatherResponse, Boolean, Exception> {
        return repo.getWeather(city)
    }
}