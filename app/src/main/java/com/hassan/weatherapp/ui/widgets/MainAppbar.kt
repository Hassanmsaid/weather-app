package com.hassan.weatherapp.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainAppbar(
    title: String = "Hello",
    icon: ImageVector? = null,
    elevation: Dp = 0.dp,
    isMainScreen: Boolean = true,
    onButtonClicked: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .padding(4.dp)
            .shadow(elevation = elevation),
        title = { Text(title) },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onButtonClicked.invoke()
                }) {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "more")
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "back",
//                    tint = Color(0xFF710909),
                    modifier = Modifier.clickable { onButtonClicked.invoke() })
            }
        },
    )
}