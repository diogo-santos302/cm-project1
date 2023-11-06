//package com.example.mydaylogger.screens
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.data.UserInfo
//import com.example.data.UserInfoRepository
//
//class EditProfileViewModel(private val userInfoRepository: UserInfoRepository) : ViewModel(){
//
////    fun onUpdateUserInfo(userInfo: UserInfo) {
////        viewModelScope.launch {
////            userInfoRepository.updateUserInfo(userInfo)
////        }
////    }
//    suspend fun updateUserInfo(userInfo: UserInfo) {
//        userInfoRepository.updateUserInfo(userInfo)
//    }
//
////    companion object {
//companion object {
//    val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return EditProfileViewModel() as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}
//
//}




