package com.hassan.weatherapp.ui.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.widgets.MainAppbar

@Composable
@Preview
fun AboutScreen(navController: NavController = rememberNavController()) {
    ScreenLayout(
        appBar = {
            MainAppbar(
                title = "About",
                navController = navController, icon = Icons.AutoMirrored.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Weather App version 1.0.0", style = MaterialTheme.typography.titleLarge)
            Text("API: https://openweathermap.org/api", style = MaterialTheme.typography.bodyMedium)
        }

    }
}