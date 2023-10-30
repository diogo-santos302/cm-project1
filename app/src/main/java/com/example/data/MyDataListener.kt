package com.example.data

//import com.google.android.gms.wearable.DataEvent
//import com.google.android.gms.wearable.DataEventBuffer
//import com.google.android.gms.wearable.DataMapItem
//import com.google.android.gms.wearable.WearableListenerService
//
//class MyDataListener : WearableListenerService() {
//    override fun onDataChanged(dataEvents: DataEventBuffer) {
//        for (event in dataEvents) {
//            if (event.type == DataEvent.TYPE_CHANGED) {
//                val path = event.dataItem.uri.path
//                if (path == "/accelerometer-data") {
//                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
//                    val xValue = dataMap.getFloat("x")
//                    val yValue = dataMap.getFloat("y")
//                    val zValue = dataMap.getFloat("z")
//                }
//            }
//        }
//    }
//}