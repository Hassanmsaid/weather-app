package com.hassan.weatherapp.ui.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun MainAppbar(
//    navController: NavController,
    title: String = "Hello",
    withBack: Boolean = false,
    icon: ImageVector? = null,
    elevation: Dp = 0.dp,
    isMainScreen: Boolean = true,
) {
    TopAppBar(
        title = { Text(title) },
//        elevation = elevation,
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "more")
                }
            }
        }
    )
}