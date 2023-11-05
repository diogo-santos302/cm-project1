package com.example.mydaylogger.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mydaylogger.BottomBarScreen
const val TAGE = "EDIT"

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Button to save profile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    Log.d(TAGE,"lol")
                    navController.navigate(BottomBarScreen.EditProfile.route)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(text = "Edit")
            }
        }

        // Rest of your ProfileScreen content goes here...
    }
}