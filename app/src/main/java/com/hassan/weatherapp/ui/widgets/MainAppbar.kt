package com.hassan.weatherapp.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hassan.weatherapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun MainAppbar(
    title: String = "Hello",
    icon: ImageVector? = null,
    elevation: Dp = 15.dp,
    isMainScreen: Boolean = false,
    navController: NavController = rememberNavController(),
    favIcon: (@Composable () -> Unit)? = null,
    onButtonClicked: () -> Unit = {},
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        ShowSettingsMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        modifier = Modifier
            .padding(0.dp)
            .shadow(elevation = elevation),
        title = { Text("  $title") },
        actions = {
            if (isMainScreen) {
                favIcon?.invoke()
                IconButton(onClick = { onButtonClicked.invoke() }) {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "more")
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "back",
                    modifier = Modifier.clickable { onButtonClicked.invoke() })
            }
        },
    )
}

@Composable
@Preview(showBackground = true)
fun ShowSettingsMenu(
    showDialog: MutableState<Boolean> = mutableStateOf(true),
    navController: NavController = rememberNavController()
) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf(
        MenuItem(icon = Icons.Outlined.Info, text = "About"),
        MenuItem(icon = Icons.Outlined.FavoriteBorder, text = "Favorites"),
        MenuItem(icon = Icons.Outlined.Settings, text = "Settings"),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .absolutePadding(right = 30.dp, top = 110.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showDialog.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.fastForEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showDialog.value = false
                        navController.navigate(
                            when (item.text) {
                                "About" -> AppScreens.AboutScreen.name
                                "Favorites" -> AppScreens.FavoritesScreen.name
                                else -> AppScreens.SettingsScreen.name
                            }
                        )
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(item.text, style = TextStyle(color = Black))
                        }
                    },
                )
            }
        }
    }
}

class MenuItem(val icon: ImageVector, val text: String)