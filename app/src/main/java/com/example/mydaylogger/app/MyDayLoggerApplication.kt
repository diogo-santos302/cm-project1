package com.example.mydaylogger.app

import android.app.Application
import com.example.mydaylogger.app.data.AppDataContainer
import com.example.mydaylogger.app.data.AppContainer

class MyDayLoggerApplication : Application() {
    private lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}