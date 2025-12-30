package com.hassan.weatherapp.ui.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun CustomNetworkImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = "https://openweathermap.org/img/wn/$url.png",
        contentDescription = contentDescription,
//        placeholder = painterResource(id = R.drawable.ic_placeholder),
//        error = painterResource(id = R.drawable.ic_error),
        modifier = modifier
    )
}