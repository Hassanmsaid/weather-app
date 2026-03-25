package com.hassan.weatherapp.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.utils.AppColors
import com.hassan.weatherapp.utils.formatDecimal

@Composable
fun WeatherInfoCircle(weatherData: WeatherResponse) {
    Surface(
        Modifier.size(250.dp), shape = CircleShape, color = AppColors.orangeColor
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${weatherData.list?.first()!!.weather!!.first().icon}.png",
                contentDescription = null,
                Modifier.size(100.dp),
            )
            Text(
                "${formatDecimal(weatherData.list.first().temp.day)}º",
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
            )
            Text(
                weatherData.list.first().weather?.get(0)!!.main,
                style = TextStyle(fontSize = 20.sp, fontStyle = Italic)
            )
        }
    }
}