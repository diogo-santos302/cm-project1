package com.example.wearosaccelerometerdata.presentation

import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.proto.DataProto.DataType
import kotlinx.coroutines.runBlocking

class PassiveDataService : PassiveListenerService() {

    val passiveListenerConfig
    override fun onNewDataPointsReceived(dataPoints: DataPointContainer) {
        super.onNewDataPointsReceived(dataPoints) {
            runBlocking {
                dataPoints.getData(DataType.HEART_RATE_BPM).latestHeartRate()?.let{

                }
            }
        }
    }

}