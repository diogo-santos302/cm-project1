package com.example.mydaylogger.screens

import androidx.lifecycle.ViewModel
import com.example.data.UserInfo
import com.example.data.UserInfoRepository

class EditProfileViewModel(private val userInfoRepository: UserInfoRepository) : ViewModel(){

//    fun onUpdateUserInfo(userInfo: UserInfo) {
//        viewModelScope.launch {
//            userInfoRepository.updateUserInfo(userInfo)
//        }
//    }
    suspend fun updateUserInfo(userInfo: UserInfo) {
        userInfoRepository.updateUserInfo(userInfo)
    }
}




