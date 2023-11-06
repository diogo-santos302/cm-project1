package com.example.mydaylogger

import android.app.Application
import com.example.mydaylogger.data.AppContainer
import com.example.mydaylogger.data.AppDataContainer

class MyDayLoggerApplication : Application() {
    private lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}