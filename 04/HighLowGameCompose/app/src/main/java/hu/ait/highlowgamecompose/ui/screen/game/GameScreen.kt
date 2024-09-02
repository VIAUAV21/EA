package hu.ait.highlowgamecompose.ui.screen

import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.highlowgamecompose.R
import hu.ait.highlowgamecompose.ui.screen.game.GameViewModel
import hu.ait.highlowgamecompose.ui.view.SimpleAlertDialog
import java.lang.Exception

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameModel: GameViewModel = viewModel()
) {
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        //var text by rememberSaveable { mutableStateOf("") }
        var text by rememberSaveable { mutableStateOf("") }
        var inputErrorState by rememberSaveable { mutableStateOf(false) }
        var errorText by rememberSaveable { mutableStateOf("") }
        var resultText by rememberSaveable { mutableStateOf("-") }
        var showDialog by rememberSaveable { mutableStateOf(false) }

        fun validate(text: String) {
            val allDigit = text.all { char -> char.isDigit() }
            errorText = "This field can be number only"
            inputErrorState = !allDigit
        }

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            isError = inputErrorState,
            onValueChange = {
                text = it
                validate(text)
            },
            label = { Text("Enter number here") },
            singleLine = true,
            keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = {
                if (inputErrorState)
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
            }
        )

        if (inputErrorState) {
            Text(
                text = "$errorText",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }



        OutlinedButton(
            enabled = text.isNotEmpty(),
            onClick = {
                try {

                    val currentNum = text.toInt()
                    if (currentNum == gameModel.generatedNum) {
                        resultText = "Congratulations, you have won!"
                        showDialog = true
                    } else if (currentNum < gameModel.generatedNum) {
                        resultText = context.getString(R.string.text_larger)
                    } else if (currentNum > gameModel.generatedNum) {
                        resultText = "The number is smaller"
                    }
                    gameModel.increaseCounter()

                } catch (e: Exception) {
                    errorText = "Error: ${e.message}"
                    inputErrorState = true
                }
            }
        ) {
            Text(stringResource(R.string.btn_guess))
        }

        OutlinedButton(
            onClick = {
                gameModel.generateNewNum()
            }
        ) {
            Text(stringResource(R.string.button_restart))
        }

        Text(text = "Counter: ${gameModel.counter}")


        Text(
            text = "$resultText",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Blue
        )

        if (showDialog) {
            SimpleAlertDialog(show = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = { showDialog = false }
            )
        }

    }
}
