package hu.ait.animationdemo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnimationScreen1() {

    var isVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row() {
            Button(onClick = {
                isVisible = true
            }) {
                Text(text = "Show")
            }
            Button(onClick = {
                isVisible = false
            }) {
                Text(text = "Hide")
            }
        }

        AnimatedVisibility(visible = isVisible,
            modifier = Modifier.fillMaxWidth().weight(1f),
            enter = slideInHorizontally() + fadeIn()
            ) {

            Box(
                modifier = Modifier.background(Color.Red)
            )
        }

    }

}