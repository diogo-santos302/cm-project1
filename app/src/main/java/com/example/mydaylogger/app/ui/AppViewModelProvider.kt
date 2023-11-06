/*package com.example.mydaylogger.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mydaylogger.MyDayLoggerApplication
import com.example.mydaylogger.screens.EditProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            EditProfileViewModel(
                myDayLoggerApplication().container.userInfoRepository
            )
        }
    }
}

fun CreationExtras.myDayLoggerApplication(): MyDayLoggerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyDayLoggerApplication)

 */