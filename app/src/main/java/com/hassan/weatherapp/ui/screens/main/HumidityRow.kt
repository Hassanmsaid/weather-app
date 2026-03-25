package com.hassan.weatherapp.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.hassan.weatherapp.R
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.ui.screens.settings.SettingsViewModel

@Composable
fun HumidityWindRow(weatherData: WeatherResponse) {
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.humidity),
                contentDescription = "humidity",
                Modifier.size(30.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text("${weatherData.list!!.first().humidity}%")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.pressure),
                contentDescription = "pressure",
                Modifier.size(30.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text("${weatherData.list!!.first().pressure}psi")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.wind),
                contentDescription = "wind",
                Modifier.size(30.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text("${weatherData.list!!.first().speed}${if (settingsViewModel.isImperial.collectAsState().value) "m/h" else "km/h"}")
        }
    }
}