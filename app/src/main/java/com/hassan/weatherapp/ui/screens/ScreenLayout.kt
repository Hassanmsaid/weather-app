package com.hassan.weatherapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenLayout(
    appBar: @Composable () -> Unit, content: @Composable () -> Unit
) {
    Scaffold(
        topBar = { appBar() }) { padding ->
        Box(
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}