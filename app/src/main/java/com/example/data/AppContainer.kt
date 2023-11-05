package com.example.data

import android.content.Context

interface AppContainer{
    val userInfoRepository: UserInfoRepository
}

class AppDataContainer (private val context: Context) : AppContainer {

    override val userInfoRepository: UserInfoRepository by lazy {
        OfflineUserInfoRepository(AppDatabase.getDatabase(context).userInfoDao())
    }



}