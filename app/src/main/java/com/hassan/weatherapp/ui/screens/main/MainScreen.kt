package com.hassan.weatherapp.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hassan.weatherapp.R
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.ui.widgets.MainAppbar
import com.hassan.weatherapp.utils.AppColors
import com.hassan.weatherapp.utils.formatDate
import com.hassan.weatherapp.utils.formatDecimal

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrException(null, true, null),
    ) {
        value = mainViewModel.getWeatherData("cairo")
    }.value
    if (weatherData.loading == true) CircularProgressIndicator()
    else MainScaffold(navController, weatherData.data!!)
}

@Composable
fun MainScaffold(navController: NavController, weatherData: WeatherResponse) {
    Scaffold(
        topBar = {
            MainAppbar(
                title = "${weatherData.city.name}, ${weatherData.city.country}",
                elevation = 2.dp,
            )
        }) { p ->
        Column(
            Modifier
                .padding(p)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainContent(weatherData)
        }
    }
}

@Composable
fun MainContent(weatherData: WeatherResponse) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherData.list!!.first().dt),
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
        )
        Spacer(Modifier.height(10.dp))
        WeatherInfoCircle(weatherData)
        Spacer(Modifier.height(10.dp))
        HumidityWindRow(weatherData)
    }
}

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
                "${formatDecimal(weatherData.list!!.first().temp.day)}º",
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
            )
            Text(
                weatherData.list.first().weather?.get(0)!!.main,
                style = TextStyle(fontSize = 20.sp, fontStyle = Italic)
            )
        }
    }
}

@Composable
fun HumidityWindRow(weatherData: WeatherResponse) {
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
            Text("${weatherData.list!!.first().speed}km/h")
        }
    }
}
