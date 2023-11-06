package com.example.mydaylogger.app.data

import android.content.Context
import com.example.mydaylogger.app.data.AppDatabase
import com.example.mydaylogger.app.data.OfflineUserInfoRepository
import com.example.mydaylogger.app.data.UserInfoRepository

interface AppContainer{
    val userInfoRepository: UserInfoRepository
}

class AppDataContainer (private val context: Context) : AppContainer {

    override val userInfoRepository: UserInfoRepository by lazy {
        OfflineUserInfoRepository(AppDatabase.getDatabase(context).userInfoDao())
    }



}