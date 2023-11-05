package com.example.mydaylogger

import android.app.Application
import com.example.data.AppContainer
import com.example.data.AppDataContainer

class MyDayLoggerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}