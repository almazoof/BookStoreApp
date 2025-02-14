package com.almazingoff.bookstoreapp.ui.main_screen.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun BottomMenu(
    selectedItem: String,
    onFavsClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Favs,
        BottomMenuItem.Settings
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item.title,
                onClick = {

                    when(item.title) {
                        BottomMenuItem.Home.title -> onHomeClick()
                        BottomMenuItem.Favs.title -> onFavsClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = item.iconId
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}