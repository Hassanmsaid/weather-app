package com.hassan.weatherapp.ui.screens.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.widgets.MainAppbar

@Composable
fun FavoritesScreen(navController: NavController = rememberNavController()) {
    ScreenLayout(
        appBar = {
            MainAppbar(
                title = "Favorites Screen",
                navController = navController,
                icon = Icons.AutoMirrored.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Text("Favorites Screen")
    }
}