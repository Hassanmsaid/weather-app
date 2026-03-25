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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hassan.weatherapp.R
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.utils.formatTime

@Composable
fun SunRow(weatherData: WeatherResponse) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Icon(
                painter = painterResource(R.drawable.sunrise),
                contentDescription = "sunrise",
                Modifier.size(30.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text(formatTime(weatherData.list!!.first().sunrise))
        }
        Row {
            Icon(
                painter = painterResource(R.drawable.sunset),
                contentDescription = "sunset",
                Modifier.size(30.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text(formatTime(weatherData.list!!.first().sunset))
        }
    }
}