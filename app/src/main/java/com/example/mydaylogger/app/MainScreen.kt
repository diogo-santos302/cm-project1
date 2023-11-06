package com.example.mydaylogger.app

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mydaylogger.app.data.AppContainer


@Composable
fun MainScreen(context: Context, appContainer: AppContainer) {
    val navController = rememberNavController()
    Scaffold (
        bottomBar = { BottomBar(navController = navController) }
    ){
        BottomNavGraph(navController = navController, context = context, appContainer = appContainer)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    //create list of destinations for the bottom bar
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Mayday,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(
        label = {
            screen.title?.let { Text(text = it) }
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Navigation Icon"
                )
            }
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route){

                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}