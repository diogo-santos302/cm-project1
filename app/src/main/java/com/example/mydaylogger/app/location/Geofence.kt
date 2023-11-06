package com.example.mydaylogger.app.location

import com.google.gson.annotations.SerializedName

data class Geofence(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("radiusInMetres") val radiusInMetres: Float
)
