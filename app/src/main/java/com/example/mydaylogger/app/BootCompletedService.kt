//package com.example.mydaylogger;
//
//import android.app.Service;
//import android.content.Intent
//import android.os.IBinder
//
//import androidx.core.app.NotificationCompat;
//
//public class BootCompletedService : Service() {
//        override fun onCreate() {
//            super.onCreate()
//
//            // Start a foreground notification to keep the service running
//            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setContentTitle("Boot Completed Service")
//                    .setContentText("This service will start when the device boots up.")
//                    //.setSmallIcon(R.drawable.ic_notification)
//                    .build()
//
//            startForeground(1, notification)
//        }
//
//        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//            // Do something when the device boots up
//            return Service.START_NOT_STICKY
//        }
//
//        override fun onDestroy() {
//            super.onDestroy()
//
//            // Stop the foreground notification
//            stopForeground(true)
//        }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//}
//}
