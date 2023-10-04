package hu.ait.advancedlayoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MagnifierStyle
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.magnifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import hu.ait.advancedlayoutdemo.ui.screen.constraintlayout.ConstraintLayoutDemo
import hu.ait.advancedlayoutdemo.ui.screen.drawer.DrawerScreen
import hu.ait.advancedlayoutdemo.ui.screen.flow.FlowScreenDemo
import hu.ait.advancedlayoutdemo.ui.screen.pager.HorizontalPagerDemo
import hu.ait.advancedlayoutdemo.ui.screen.scaffold.ScaffoldDemoScreen
import hu.ait.advancedlayoutdemo.ui.theme.AdvancedLayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedLayoutDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    //MagnifierDemo()
                    // Uncomment this to try pager
                    //HorizontalPagerDemo()

                    // Uncomment this to try flow layout
                    //FlowScreenDemo()

                    // Uncomment this to try constraint layout
                    //ConstraintLayoutDemo()

                    // Uncomment this to try scaffold layout
                    ScaffoldDemoScreen()

                    // Uncomment this to try navigation drawer layout
                    //DrawerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MagnifierDemo() {
    var magnifierCenter by remember {
        mutableStateOf(Offset.Unspecified)
    }
    Box(
        Modifier
            // Set the source center and styles for the magnifier
            .magnifier(
                sourceCenter = { magnifierCenter },
                zoom = 3f,
                style = MagnifierStyle(
                    size = DpSize(height = 200.dp, width = 300.dp),
                ),
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    // Show the magnifier in the initial position
                    onDragStart = { magnifierCenter = it },
                    // Magnifier follows the pointer during a drag event
                    onDrag = { _, delta ->
                        magnifierCenter = magnifierCenter.plus(delta)
                    },
                    // Hide the magnifier when a user ends the drag movement.
                    onDragEnd = { magnifierCenter = Offset.Unspecified },
                    onDragCancel = { magnifierCenter = Offset.Unspecified },
                )
            },
    ) {
        Text(
            "Try magnifying this text by dragging a pointer (finger, mouse, other) over the text.",
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.blur(2.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdvancedLayoutDemoTheme {
        Greeting("Android")
    }
}