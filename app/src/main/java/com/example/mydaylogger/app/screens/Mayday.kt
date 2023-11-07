package com.example.mydaylogger.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mydaylogger.app.data.StorePhoneNumber
import com.example.mydaylogger.app.notifications.Alarm

@Composable
fun MaydayScreen() {
    val context = LocalContext.current
    val dataStore = StorePhoneNumber(context)
    val savedPhoneNumber = dataStore.getPhoneNumber.collectAsState(initial = "edit").value
    val alarm = Alarm(context)
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Mayday Page",
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
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "In case of distress, press button to warn caretaker:",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp), // Increase the height of the button
                onClick = { alarm.send(savedPhoneNumber) }
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Mayday button",
                    modifier = Modifier.size(48.dp) // Increase the size of the icon
                )
                Text(
                    text = "Mayday",
                    fontSize = 18.sp // Increase the font size of the text
                )
            }
        }
    }
}
