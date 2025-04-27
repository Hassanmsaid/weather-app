package com.hassan.weatherapp.ui.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.models.WeatherResponse

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {
    val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrException(null, true, null),
    ) {
        value = mainViewModel.getWeatherData("cairo")
    }.value

    if (weatherData.loading == true)
        CircularProgressIndicator()
    else
        Text(weatherData.data.toString())
}