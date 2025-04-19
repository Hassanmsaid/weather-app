package com.hassan.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.ui.screens.MainScreen
import com.hassan.weatherapp.ui.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(route = AppScreens.MainScreen.name) {
            MainScreen(navController)
        }
    }
}