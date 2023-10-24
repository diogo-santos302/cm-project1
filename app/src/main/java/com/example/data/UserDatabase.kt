package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//creating singleton for room database

@Database (entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

    //everything inside is going to be visible to other classes
    //if instance already exists return the same instance, otherwise in the synchronized bloch we
    // create a new instance, always using the same instance for room database
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null


        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}