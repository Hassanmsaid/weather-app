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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
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
import com.hassan.weatherapp.models.Favorite
import com.hassan.weatherapp.models.WeatherObject
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.navigation.AppScreens
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.screens.favorites.FavoriteViewModel
import com.hassan.weatherapp.ui.widgets.CustomNetworkImage
import com.hassan.weatherapp.ui.widgets.ErrorWidget
import com.hassan.weatherapp.ui.widgets.MainAppbar
import com.hassan.weatherapp.utils.AppColors
import com.hassan.weatherapp.utils.AppColors.lightGreyColor
import com.hassan.weatherapp.utils.formatDate
import com.hassan.weatherapp.utils.formatDecimal
import com.hassan.weatherapp.utils.formatTime
import com.hassan.weatherapp.utils.getWeekdayFromTimestamp
import com.hassan.weatherapp.utils.loadJsonFromAssets

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String = "Dubai"
) {
    println("MainScreen: $city")

    var refreshTrigger by remember { mutableIntStateOf(0) }

    val weatherData = produceState<DataOrException<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrException(null, true, null), key1 = refreshTrigger
    ) {
        value = mainViewModel.getWeatherData(city)
    }.value
    if (weatherData.loading == true) CircularProgressIndicator()
    else if (weatherData.e != null) {
        ErrorWidget(error = weatherData.e.toString(), onTryAgain = { refreshTrigger++ })
    } else MainScaffold(navController, weatherData.data!!, hiltViewModel<FavoriteViewModel>())
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    navController: NavController, weatherData: WeatherResponse, viewModel: FavoriteViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val message = viewModel.toastMessage.collectAsState()
    LaunchedEffect(message.value) {
        message.value.let {
            if (message.value.isNotEmpty()) {
                snackBarHostState.showSnackbar(message.value)
                viewModel.clearToast()
            }
        }
    }
    ScreenLayout(
        snackBarHostState = snackBarHostState,
        appBar = {
            MainAppbar(
                title = "${weatherData.city.name}, ${weatherData.city.country}",
                navController = navController,
                isMainScreen = true,
                favIcon = {
                    IconButton(enabled = true, onClick = {
                        viewModel.toggleFavorite(
                            Favorite(
                                city = weatherData.city.name, country = weatherData.city.country
                            )
                        )
                    }) {
                        if (viewModel.isFavorite(weatherData.city.name)
                                .collectAsState(false).value
                        ) Icon(
                            Icons.Default.Favorite, contentDescription = "fav", tint = Red
                        )
                        else Icon(Icons.Default.FavoriteBorder, contentDescription = "fav")
                    }
                }) {
                navController.navigate(AppScreens.SearchScreen.name)
            }
        }) {
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
    weatherData: WeatherResponse = Gson().fromJson(
        loadJsonFromAssets(LocalContext.current, "weather_response.json").trimIndent(),
        WeatherResponse::class.java
    )
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp), color = lightGreyColor
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
                        fontWeight = FontWeight.Bold, color = AppColors.blueColor, fontSize = 18.sp
                    )
                )
                Text(" ")
                Text(
                    formatDecimal(weatherObject.temp.max) + "º",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold, color = AppColors.greyColor, fontSize = 18.sp
                    ),
//                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


