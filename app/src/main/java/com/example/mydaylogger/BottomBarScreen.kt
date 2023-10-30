package com.example.mydaylogger

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String?,
    val icon: ImageVector?
){
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Mayday: BottomBarScreen(
        route = "mayday",
        title = "Mayday",
        icon = Icons.Default.Warning
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object EditProfile: BottomBarScreen("edit_profile", null, null)

}
