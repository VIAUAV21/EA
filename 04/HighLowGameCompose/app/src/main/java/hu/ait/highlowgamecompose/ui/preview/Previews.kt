package hu.ait.highlowgamecompose.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.highlowgamecompose.MyAppNavHost
import hu.ait.highlowgamecompose.ui.theme.HighLowGameComposeTheme


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HighLowGameComposeTheme {
        MyAppNavHost(modifier = Modifier.fillMaxSize())
    }
}