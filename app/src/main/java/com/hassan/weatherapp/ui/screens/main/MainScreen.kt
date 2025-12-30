package com.hassan.weatherapp.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.hassan.weatherapp.R
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.models.WeatherObject
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.ui.widgets.CustomNetworkImage
import com.hassan.weatherapp.ui.widgets.MainAppbar
import com.hassan.weatherapp.utils.AppColors
import com.hassan.weatherapp.utils.AppColors.lightGreyColor
import com.hassan.weatherapp.utils.formatDate
import com.hassan.weatherapp.utils.formatDecimal
import com.hassan.weatherapp.utils.formatTime
import com.hassan.weatherapp.utils.getWeekdayFromTimestamp

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrException(null, true, null),
    ) {
        value = mainViewModel.getWeatherData("dubai")
    }.value
    if (weatherData.loading == true) CircularProgressIndicator()
    else MainScaffold(navController, weatherData.data!!)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                .padding(0.dp)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainContent(weatherData)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContent(
    weatherData: WeatherResponse =
        Gson().fromJson(
            """
                        {
                        "city": {
                        "id": 360630,
                        "name": "Cairo",
                        "coord": {
                        "lon": 31.2497,
                        "lat": 30.0626
                        },
                        "country": "EG",
                        "population": 7734614,
                        "timezone": 10800
                        },
                        "cod": "200",
                        "message": 0.9570391,
                        "cnt": 7,
                        "list": [
                        {
                        "dt": 1746003600,
                        "sunrise": 1745982774,
                        "sunset": 1746030680,
                        "temp": {
                        "day": 29.2,
                        "min": 19.59,
                        "max": 29.48,
                        "night": 19.59,
                        "eve": 26.58,
                        "morn": 22.61
                        },
                        "feels_like": {
                        "day": 28.46,
                        "night": 19.17,
                        "eve": 26.58,
                        "morn": 22.36
                        },
                        "pressure": 1005,
                        "humidity": 36,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 11.02,
                        "deg": 287,
                        "gust": 12.84,
                        "clouds": 6,
                        "pop": 0
                        },
                        {
                        "dt": 1746090000,
                        "sunrise": 1746069121,
                        "sunset": 1746117119,
                        "temp": {
                        "day": 24.38,
                        "min": 16.89,
                        "max": 27.42,
                        "night": 19.68,
                        "eve": 26.47,
                        "morn": 16.89
                        },
                        "feels_like": {
                        "day": 23.66,
                        "night": 19.09,
                        "eve": 26.47,
                        "morn": 16.51
                        },
                        "pressure": 1012,
                        "humidity": 30,
                        "weather": [
                        {
                        "id": 802,
                        "main": "Clouds",
                        "description": "scattered clouds",
                        "icon": "03d"
                        }
                        ],
                        "speed": 8.52,
                        "deg": 324,
                        "gust": 10.01,
                        "clouds": 44,
                        "pop": 0
                        },
                        {
                        "dt": 1746176400,
                        "sunrise": 1746155469,
                        "sunset": 1746203559,
                        "temp": {
                        "day": 25.18,
                        "min": 17.52,
                        "max": 27.27,
                        "night": 20.58,
                        "eve": 26.32,
                        "morn": 17.52
                        },
                        "feels_like": {
                        "day": 24.28,
                        "night": 19.92,
                        "eve": 26.32,
                        "morn": 16.92
                        },
                        "pressure": 1013,
                        "humidity": 20,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 8.92,
                        "deg": 265,
                        "gust": 10.06,
                        "clouds": 0,
                        "pop": 0
                        },
                        {
                        "dt": 1746262800,
                        "sunrise": 1746241818,
                        "sunset": 1746289999,
                        "temp": {
                        "day": 23.3,
                        "min": 17.22,
                        "max": 25.62,
                        "night": 21.98,
                        "eve": 24.77,
                        "morn": 17.22
                        },
                        "feels_like": {
                        "day": 22.81,
                        "night": 21.43,
                        "eve": 24.24,
                        "morn": 16.83
                        },
                        "pressure": 1013,
                        "humidity": 43,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 6.15,
                        "deg": 352,
                        "gust": 6.71,
                        "clouds": 1,
                        "pop": 0
                        },
                        {
                        "dt": 1746349200,
                        "sunrise": 1746328169,
                        "sunset": 1746376439,
                        "temp": {
                        "day": 23.18,
                        "min": 17.49,
                        "max": 26.4,
                        "night": 22.58,
                        "eve": 25.68,
                        "morn": 17.49
                        },
                        "feels_like": {
                        "day": 22.65,
                        "night": 21.96,
                        "eve": 25.19,
                        "morn": 17.15
                        },
                        "pressure": 1014,
                        "humidity": 42,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 8.94,
                        "deg": 347,
                        "gust": 8.37,
                        "clouds": 3,
                        "pop": 0
                        },
                        {
                        "dt": 1746435600,
                        "sunrise": 1746414520,
                        "sunset": 1746462879,
                        "temp": {
                        "day": 23.03,
                        "min": 16.62,
                        "max": 29.23,
                        "night": 24.25,
                        "eve": 29.23,
                        "morn": 16.62
                        },
                        "feels_like": {
                        "day": 22.43,
                        "night": 23.41,
                        "eve": 27.55,
                        "morn": 16.35
                        },
                        "pressure": 1014,
                        "humidity": 40,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 6.61,
                        "deg": 26,
                        "gust": 7.85,
                        "clouds": 0,
                        "pop": 0
                        },
                        {
                        "dt": 1746522000,
                        "sunrise": 1746500872,
                        "sunset": 1746549319,
                        "temp": {
                        "day": 26.91,
                        "min": 19.41,
                        "max": 31.54,
                        "night": 26.53,
                        "eve": 31.54,
                        "morn": 19.41
                        },
                        "feels_like": {
                        "day": 26.06,
                        "night": 26.53,
                        "eve": 29.38,
                        "morn": 18.56
                        },
                        "pressure": 1016,
                        "humidity": 21,
                        "weather": [
                        {
                        "id": 800,
                        "main": "Clear",
                        "description": "sky is clear",
                        "icon": "01d"
                        }
                        ],
                        "speed": 9.45,
                        "deg": 40,
                        "gust": 11.55,
                        "clouds": 0,
                        "pop": 0
                        }
                        ]
                        }
                    """.trimIndent(), WeatherResponse::class.java
        )
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 70.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))
        Text(
            text = formatDate(weatherData.list!!.first().dt),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black, fontWeight = W500
            ),
        )
        Spacer(Modifier.height(10.dp))
        WeatherInfoCircle(weatherData)
        Spacer(Modifier.height(20.dp))
        HumidityWindRow(weatherData)
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))
        SunRow(weatherData)
        Spacer(Modifier.height(10.dp))
        Text(
            "This Week", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        )
        Surface(
            color = lightGreyColor,
            shape = RoundedCornerShape(8.dp),
        ) {
            LazyColumn(Modifier.padding(8.dp)) {
                items(weatherData.list.size) { i ->
                    WeatherDetailRow(weatherData.list[i])
                    if (i < weatherData.list.size - 1) {
                        Spacer(Modifier.height(6.dp))
                    }
                }
            }
        }
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

@Composable
fun WeatherDetailRow(weatherObject: WeatherObject) {
    Surface(
        shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp),
        color = lightGreyColor
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.large,
                )
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(0.15f),
                text = getWeekdayFromTimestamp(weatherObject.dt).substring(0, 3)
            )
            CustomNetworkImage(
                url = weatherObject.weather!!.first().icon,
                contentDescription = "sunset",
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.2f),
            )
            Surface(
                modifier = Modifier.weight(0.2f),
                shape = CircleShape,
                color = (AppColors.orangeColor)
            ) {
                Text(
                    text = weatherObject.weather.first().main,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier.weight(0.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    formatDecimal(weatherObject.temp.min) + "º", style = TextStyle(
                        fontWeight = FontWeight.Bold, color = AppColors.blueColor,
                        fontSize = 18.sp
                    )
                )
                Text(" ")
                Text(
                    formatDecimal(weatherObject.temp.max) + "º",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, color = AppColors.greyColor,
                        fontSize = 18.sp
                    ),
//                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


