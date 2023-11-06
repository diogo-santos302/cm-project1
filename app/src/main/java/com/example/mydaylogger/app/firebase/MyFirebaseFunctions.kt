package com.example.mydaylogger.app.firebase

import com.google.firebase.Firebase
import com.google.firebase.functions.HttpsCallableReference
import com.google.firebase.functions.functions

class MyFirebaseFunctions {
    private val functions = Firebase.functions

    init {
        functions.useEmulator("10.0.2.2", 5001)
    }

    fun getHttpsCallable(functionName: String): HttpsCallableReference {
        return functions.getHttpsCallable(functionName)
    }
}