package com.example.flowemitrandomdemo.screen

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class MainRepository {

    var enableFlow = false

    val latestDateFlow: Flow<String> = flow {
        while(enableFlow) {
            emit(Date(System.currentTimeMillis()).toString())
            delay(3000) // Suspends the coroutine for some time
        }
    }

    fun startFlow() {
        enableFlow = true
    }
    fun stopFlow() {
        enableFlow = false
    }


}