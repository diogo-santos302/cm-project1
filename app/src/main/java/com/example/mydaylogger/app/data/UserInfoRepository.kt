package com.example.mydaylogger.app.data

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
//    fun getUserInfoStream(): Flow<List<UserInfo>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getUserInfoStream(id: Int): Flow<UserInfo?>

    /**
     * Insert item in the data source
     */
    suspend fun insertUserInfo(userInfo: UserInfo)

    /**
     * Delete item from the data source
     */
    suspend fun deleteUserInfo(userInfo: UserInfo)

    /**
     * Update item in the data source
     */
    suspend fun updateUserInfo(userInfo: UserInfo)
}