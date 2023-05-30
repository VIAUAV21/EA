package hu.ait.animationdemo

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationScreen3() {
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

        AnimatedContent(targetState = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            transitionSpec = {
                //fadeIn() with fadeOut()

                slideInHorizontally(
                    initialOffsetX = { -it }
                ) with fadeOut()
            }
        ) { isVisible ->
            if (isVisible) {
                Box(modifier = Modifier.background(Color.Green))
            } else {
                Box(modifier = Modifier.background(Color.Red))
            }
        }
    }
}