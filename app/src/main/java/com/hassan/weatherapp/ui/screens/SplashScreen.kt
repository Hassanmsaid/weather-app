package com.hassan.weatherapp.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.R
import com.hassan.weatherapp.navigation.AppScreens
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun SplashScreen(navController: NavController = rememberNavController()) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(
        key1 = true,
        block = {
            scale.animateTo(
                targetValue = 0.9f, animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(6f).getInterpolation(it)
                    }
                )
            )
            delay(timeMillis = 2000)
            navController.navigate(AppScreens.MainScreen.name)
        },
    )
    Surface(
        Modifier
            .size(300.dp)
            .scale(scale.value),
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = Color.LightGray),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.sun),
                contentDescription = "",
            )
            Spacer(Modifier.height(10.dp))
            Text("Found the sun?", style = TextStyle(fontSize = 25.sp, color = Color.LightGray))
        }
    }
}