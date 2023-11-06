package com.example.mydaylogger.app.data

import kotlinx.coroutines.flow.Flow

class OfflineUserInfoRepository(private val userInfoDao: UserInfoDao): UserInfoRepository {
    //override fun getUserInfoStream(): Flow<List<UserInfo>> = userInfoDao.

    override fun getUserInfoStream(id: Int): Flow<UserInfo?> = userInfoDao.getUserInfo(id)

    override suspend fun insertUserInfo(userInfo: UserInfo) =userInfoDao.insert(userInfo)

    override suspend fun deleteUserInfo(userInfo: UserInfo) =userInfoDao.delete(userInfo)

    override suspend fun updateUserInfo(userInfo: UserInfo) =userInfoDao.update(userInfo)
}