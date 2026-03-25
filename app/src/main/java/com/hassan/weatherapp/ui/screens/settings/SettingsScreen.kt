package com.hassan.weatherapp.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.widgets.MainAppbar

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    viewModel: SettingsViewModel = hiltViewModel()
) {
    ScreenLayout(
        appBar = {
            MainAppbar(
                title = "Settings Screen",
                navController = navController,
                icon = Icons.AutoMirrored.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }) {
        SettingContent(
            checked = viewModel.isImperial.collectAsState().value,
            onCheckedChanged = { viewModel.toggleMeasureUnit() })
    }
}

@Composable
@Preview(showBackground = true)
fun SettingContent(checked: Boolean = true, onCheckedChanged: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                "Set Temp Measurement Unit", style = TextStyle(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Metric")
                Spacer(Modifier.width(13.dp))
                Switch(
                    checked = checked, onCheckedChange = { onCheckedChanged() })
                Spacer(Modifier.width(13.dp))
                Text("Imperial")
            }
        }
    }
}