package com.example.flowemitrandomdemo.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel : ViewModel() {

    var dateValue by mutableStateOf("")

    var mainRepository: MainRepository = MainRepository()

    init {
        mainRepository.startFlow()
        viewModelScope.launch {
            mainRepository.latestDateFlow.collect { date ->
                dateValue = date
            }
        }
    }

    fun stopFlow() {
        mainRepository.stopFlow()
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.stopFlow()
    }


}