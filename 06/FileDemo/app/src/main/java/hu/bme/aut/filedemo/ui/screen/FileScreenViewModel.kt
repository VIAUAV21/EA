package hu.bme.aut.filedemo.ui.screen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

sealed interface FileScreenUiState {
    object Init : FileScreenUiState
    object Loading : FileScreenUiState
    object Success : FileScreenUiState
    data class Error(val error: String?) : FileScreenUiState
}

class FileScreenViewModel() : ViewModel() {

    var fileScreenUiState: FileScreenUiState by mutableStateOf(FileScreenUiState.Init)

    fun writeFile(context: Context, data: String) {
        viewModelScope.launch {
            fileScreenUiState = FileScreenUiState.Loading
            lateinit var outputStream: FileOutputStream
            try {
                outputStream = context.openFileOutput("text.txt", Context.MODE_PRIVATE)
                outputStream.write(data.toByteArray())
                outputStream.flush()
                fileScreenUiState = FileScreenUiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                fileScreenUiState = FileScreenUiState.Error(e.message)
            } finally {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun readFile(
        context: Context,
        onReadReady: (String) -> Unit
    ) {
        viewModelScope.launch {
            fileScreenUiState = FileScreenUiState.Loading
            lateinit var inputStream: FileInputStream
            try {
                inputStream = context.openFileInput("text.txt")
                val result = inputStream.bufferedReader().use { it.readText() }
                onReadReady(result)
                fileScreenUiState = FileScreenUiState.Success
            } catch (e: Exception) {
                e.printStackTrace()
                fileScreenUiState = FileScreenUiState.Error(e.message)
            } finally {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}