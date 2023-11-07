package hu.ait.advancedlayoutdemo.ui.screen.constraintlayout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import hu.ait.advancedlayoutdemo.R


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val topBarRef = createRef()
        val logoRef = createRef()
        val usernameRef = createRef()
        val passwordRef = createRef()
        val loginButtonRef = createRef()

        // Top bar
        Box(
            modifier = Modifier
                .constrainAs(topBarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(16.dp)
                .background(Color.White)
        ) {
            Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        }

        // Logo
        Image(
            painter = painterResource(R.drawable.mountain1),
            contentDescription = "Logo",
            modifier = Modifier
                .constrainAs(logoRef) {
                    top.linkTo(topBarRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .width(100.dp)
                .height(100.dp)
        )

        // Username
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Username") },
            modifier = Modifier
                .constrainAs(usernameRef) {
                    top.linkTo(logoRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth()
        )

        // Password
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Password") },
            modifier = Modifier
                .constrainAs(passwordRef) {
                    top.linkTo(usernameRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth()
        )

        // Login button
        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(loginButtonRef) {
                    top.linkTo(passwordRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}
