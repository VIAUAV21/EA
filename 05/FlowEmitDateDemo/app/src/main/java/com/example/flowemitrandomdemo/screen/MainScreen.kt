package com.example.flowemitrandomdemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Column {
        Text("${viewModel.dateValue}")
        Button(onClick = {
            viewModel.stopFlow()
        }) {
            Text("Stop flow")
        }
    }


}