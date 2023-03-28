package hu.ait.highlowgamecompose.ui.screen.help

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HelpScreen(
    modifier: Modifier = Modifier,
    helpText: String = "Use the menu to navigate in the application",
    userId: Int = 0
) {
    Text(text = "$helpText, userId: $userId")
}