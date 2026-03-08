package com.hassan.weatherapp.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorWidget(error: String?, onTryAgain: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(error ?: "Unexpected error occurred, please try again!")
        Spacer(Modifier.height(10.dp))
        Button(onClick = onTryAgain) {
            Text("Try Again!")
        }
    }
}