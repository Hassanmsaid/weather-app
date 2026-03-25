package com.hassan.weatherapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hassan.weatherapp.models.WeatherObject
import com.hassan.weatherapp.ui.widgets.CustomNetworkImage
import com.hassan.weatherapp.utils.AppColors
import com.hassan.weatherapp.utils.AppColors.lightGreyColor
import com.hassan.weatherapp.utils.formatDecimal
import com.hassan.weatherapp.utils.getWeekdayFromTimestamp

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
                )
            }
        }
    }
}