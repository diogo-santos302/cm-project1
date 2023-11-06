package com.example.mydaylogger.app.location

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

private const val TAG = "TestLocation"

@Composable
fun TestLocation(context: Context, modifier: Modifier = Modifier) {
    var currentLocation: Location? by remember { mutableStateOf(null) }
    CurrentLocationService(context).getLocationPermissions()
    CurrentLocationService(context).getCurrentLocationOrNull {
        currentLocation = it
    }
    Log.d(TAG, currentLocation?.latitude?.toString() + "," + currentLocation?.longitude?.toString())
    Text(
        text = currentLocation?.latitude?.toString() + "," + currentLocation?.longitude?.toString()
    )
}