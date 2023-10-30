package com.example.mydaylogger

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mydaylogger.screens.EditProfileScreen
import com.example.mydaylogger.screens.HomeScreen
import com.example.mydaylogger.screens.MaydayScreen
import com.example.mydaylogger.screens.ProfileScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route){
            HomeScreen()
        }
        composable(route = BottomBarScreen.Mayday.route){
            MaydayScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen(navController = navController)//navController = appState.navController)
        }
        composable(route = BottomBarScreen.EditProfile.route){
            EditProfileScreen(navController = navController, viewModel = viewModel())
        }
    }

}