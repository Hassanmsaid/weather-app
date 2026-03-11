package com.hassan.weatherapp.ui.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.navigation.AppScreens
import com.hassan.weatherapp.ui.screens.ScreenLayout
import com.hassan.weatherapp.ui.widgets.CustomTextField
import com.hassan.weatherapp.ui.widgets.MainAppbar

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
fun SearchScreen(navController: NavController = rememberNavController()) {
    ScreenLayout(
        appBar = {
            MainAppbar(
                title = "Search",
                isMainScreen = false,
                icon = Icons.AutoMirrored.Default.ArrowBack,
                onButtonClicked = { navController.popBackStack() })
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Log.i("", "SearchScreen: $it")
                    navController.navigate(AppScreens.MainScreen.name + "/$it")
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}
) {
    val searchQueryState: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            valueState = searchQueryState, placeholder = "Seattle", onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }, imeAction = ImeAction.Done
        )
    }
}
