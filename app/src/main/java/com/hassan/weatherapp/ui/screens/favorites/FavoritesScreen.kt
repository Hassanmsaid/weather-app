package com.hassan.weatherapp.ui.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.models.Favorite
import com.hassan.weatherapp.navigation.AppScreens
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.widgets.MainAppbar

@Composable
fun FavoritesScreen(
    navController: NavController = rememberNavController(),
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    ScreenLayout(
        appBar = {
            MainAppbar(
                title = "Favorites",
                navController = navController,
                icon = Icons.AutoMirrored.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }) {
        val favs = viewModel.favorites.collectAsState().value
        LazyColumn() {
            items(favs) { fav ->
                FavoriteRow(
                    fav = fav,
                    onDelete = { viewModel.toggleFavorite(fav) },
                    navController = navController
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FavoriteRow(
    fav: Favorite = Favorite(city = "Seattle", country = "USA"),
    onDelete: (Favorite) -> Unit = {},
    navController: NavController = rememberNavController()
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    navController.navigate(AppScreens.MainScreen.name + "/${fav.city}") {
                        // Pop up to the old MainScreen and remove it (inclusive = true)
                        popUpTo("${AppScreens.MainScreen.name}/{city}") {
                            inclusive = true
                        }
                        // Optional: Avoid multiple copies of the same screen if the user taps twice
                        launchSingleTop = true
                    }
                }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fav.city, modifier = Modifier.weight(0.25f))
        Row(modifier = Modifier.weight(0.2f)) {
            Surface(color = Color(0xFFFFEDB1)) {
                Text(
                    fav.country,
                    modifier = Modifier.padding(4.dp),
                )
            }
        }
        IconButton(
            modifier = Modifier.weight(0.2f),
            colors = IconButtonColors(
                containerColor = Color.White,
                contentColor = Color.Red,
                disabledContainerColor = Color.Green,
                disabledContentColor = Color.Red
            ),
            onClick = { onDelete(fav) },
        ) {
            Icon(
                Icons.Outlined.Delete, contentDescription = "Delete"
            )
        }
    }
}