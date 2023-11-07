package com.example.mydaylogger.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydaylogger.app.ui.HeartRateCard
import com.example.mydaylogger.app.ui.StepsCard

@Composable
fun HomeScreen(){
    Column {

        TopAppBar(
            title = {
                Text(
                    text = "My Day Logger",
                    style = TextStyle(color = MaterialTheme.colors.onPrimary),
                    fontSize = 24.sp)
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            StepsCard(currentSteps = 1000, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp)) // Add some vertical space

            HeartRateCard(values = listOf(74, 60, 82, 90, 105, 69, 55))
        }
    }
}