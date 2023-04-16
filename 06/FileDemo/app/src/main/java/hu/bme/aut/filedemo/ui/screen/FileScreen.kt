package hu.bme.aut.filedemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

@Composable
fun FileScreen(
    fileScreenViewModel: FileScreenViewModel = viewModel(),
) {
    val context = LocalContext.current
    var textToWriteToFile by rememberSaveable { mutableStateOf("") }
    var fileContent by rememberSaveable { mutableStateOf("") }
    Column() {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            label = {
                Text(text = "Content")
            },
            value = textToWriteToFile,
            onValueChange = {
                textToWriteToFile = it
            }
        )
        OutlinedButton(onClick = {
            fileScreenViewModel.writeFile(context,
                "[${Date(System.currentTimeMillis()).toString()}] - $textToWriteToFile"
            )
        }) {
            Text(text = "Write to file")
        }
        OutlinedButton(onClick = {
            fileScreenViewModel.readFile(
                context,
                {fileText -> fileContent = fileText}
            )
        }) {
            Text(text = "Read from file")
        }
        Text(text = "File content: $fileContent")

        when (fileScreenViewModel.fileScreenUiState) {
            is FileScreenUiState.Loading -> CircularProgressIndicator()
            is FileScreenUiState.Success -> Text(text = "Fire operation OK")
            is FileScreenUiState.Error -> Text(text = "${(fileScreenViewModel.fileScreenUiState as FileScreenUiState.Error).error}")
            FileScreenUiState.Init -> {}
        }
    }

}