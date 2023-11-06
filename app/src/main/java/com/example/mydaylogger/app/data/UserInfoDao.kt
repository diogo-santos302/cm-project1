package com.example.mydaylogger.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Data Access Object, contains the methods used to access the database

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //will only be updating to one place
    suspend fun insert(vararg userInfo: UserInfo)

    @Update
    suspend fun update(userInfo: UserInfo)

    @Delete
    suspend fun delete(userInfo: UserInfo)

    @Query("SELECT * from user_table WHERE id=:id")
    fun getUserInfo(id: Int): Flow<UserInfo>

    @Query ("SELECT MAX(id) + 1 FROM user_table")
    fun getNextId(): Int

}