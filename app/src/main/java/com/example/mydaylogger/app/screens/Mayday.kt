package com.example.mydaylogger.app.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.mydaylogger.app.data.StorePhoneNumber
import com.example.mydaylogger.app.notifications.Alarm

private const val TAG = "Mayday"

@Composable
fun MaydayScreen() {
    val context = LocalContext.current
    val dataStore = StorePhoneNumber(context)
    val savedPhoneNumber = dataStore.getPhoneNumber.collectAsState(initial = "edit").value
    val alarm = Alarm(context)
    Button(onClick = { alarm.send(savedPhoneNumber) }) {
        Text(text = "Alarm")
    }
}

