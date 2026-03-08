package com.hassan.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hassan.weatherapp.ui.screens.main.MainScreen
import com.hassan.weatherapp.ui.screens.search.SearchScreen
import com.hassan.weatherapp.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(navController)
        }

        val route = "${AppScreens.MainScreen.name}/{city}"
        composable(
            route = route, arguments = listOf(
                navArgument(
                    name = "city"
                ) {
                    type = NavType.StringType
                })
        ) {
            it.arguments?.getString("city").let { city ->
                MainScreen(navController, city = city ?: "")
            }
        }

        composable(route = AppScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
    }
}