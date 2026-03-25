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