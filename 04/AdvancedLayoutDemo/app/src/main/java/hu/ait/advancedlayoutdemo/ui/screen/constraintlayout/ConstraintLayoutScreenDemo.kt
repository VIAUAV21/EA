package hu.ait.advancedlayoutdemo.ui.screen.constraintlayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun ConstraintLayoutDemo() {
    ConstraintLayout {

        val startGuideline = createGuidelineFromStart(0.6f)
        val centerGuideline = createGuidelineFromTop(0.8f)

        val (button, text, button3) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) { Text("Button") }
        Text("Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp) }
        )
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button3) {
                top.linkTo(centerGuideline, margin = 16.dp)
                bottom.linkTo(centerGuideline, margin = 16.dp)
                start.linkTo(startGuideline, margin = 24.dp)
            }
        ) { Text("Button") }
    }
}