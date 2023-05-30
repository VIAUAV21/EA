package hu.ait.animationdemo

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimationScreen2() {
    var sizeState by remember { mutableStateOf(200.dp) }

    val size by animateDpAsState(
        targetValue = sizeState,
        /*tween(
            durationMillis =  3000,
            delayMillis = 300,
            easing = LinearOutSlowInEasing
        )*/
        /*spring(
            Spring.DampingRatioHighBouncy
        )*/
        keyframes {
            durationMillis = 5000
            sizeState * 0.8f at 1000 with LinearEasing
            sizeState * 1f at 5000 with FastOutLinearInEasing
        }
    )

    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            sizeState += 50.dp
        }) {
            Text(text = "Increase")
        }

        Box(
            modifier = Modifier
                .size(size)
                .background(color)
        )
    }
}