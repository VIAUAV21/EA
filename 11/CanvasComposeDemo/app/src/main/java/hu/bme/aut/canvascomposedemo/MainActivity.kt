package hu.bme.aut.canvascomposedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hu.bme.aut.canvascomposedemo.ui.theme.CanvasComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CanvasComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MyCanvas()
                    //CanvasDemoScreen()
                    //ComposeDrawBehind()
                    AnimatedCanvasScreen()
                }
            }
        }
    }
}

@Composable
fun MyCanvas() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRect(
                color = Color.Blue,
                size = size
            )
            drawLine(
                start = Offset(x = size.width, y = 0f),
                end = Offset(x = 0f, y = size.height),
                color = Color.Green,
                strokeWidth = 8f)
        }
    }
}



@Composable
fun ComposeDrawBehind() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .drawBehind {
                    drawRect(
                        color = Color.Blue,
                        size = size
                    )
                }
        )
    }
}


@Composable
fun CanvasDemoScreen() {

    var taps by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .pointerInput(key1 = Unit) {
                    /*detectTapGestures {
                        taps+=it
                    }*/
                    detectDragGestures(
                        onDragStart = { touch ->
                            Log.i("Tag", "Start of the interaction is $touch")
                            points = listOf(touch)
                        },
                        onDrag = { change, dragAmount ->
                            Log.i("Tag", "Dragged $dragAmount; Result $change")
                            val newPoint = change.position
                            points = points + newPoint
                        }
                    )
                }
        )
        {
            drawRect(
                color = Color.Blue,
                size = size
            )


            taps.forEach {
                drawCircle(brush = Brush.horizontalGradient(
                    colors = listOf(Color.Green, Color.Yellow),
                ), radius =  40f, style= Stroke(width = 8.dp.toPx()), center = it)
            }

            if (points.size > 1) {
                val path = Path().apply {
                    val firstPoint = points.first()
                    val rest = points.subList(1, points.size - 1)

                    moveTo(firstPoint.x, firstPoint.y)
                    rest.forEach {
                        lineTo(it.x, it.y)
                    }
                }

                drawPath(path, Color.Red, style = Stroke(width = 5.dp.toPx()))
            }
        }
    }
}

@Composable
fun AnimatedCanvasScreen(componentSize: Dp = 300.dp) {
    val canvasSizePx = with(LocalDensity.current) {
        componentSize.toPx()
    }

    val infiniteScale = rememberInfiniteTransition()

    val animatedDotScale by infiniteScale.animateFloat(
        initialValue = 20f,
        targetValue = canvasSizePx / 2,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "anim"
    )

    Canvas(
        modifier = Modifier
            .size(componentSize)
    ) {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Blue)
            ),
            size = size
        )

        drawCircle(
            color = Color.White,
            center = Offset(x = size.width / 2f, y = size.height / 2f),
            radius = animatedDotScale
        )
    }
}