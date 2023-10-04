package hu.ait.advancedlayoutdemo.ui.screen.flow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowScreenDemo() {
    FlowRow(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Price: High to Low", modifier =  Modifier.border(BorderStroke(1.dp, Color.Blue)))
        Text("Avg rating: 4+", modifier =  Modifier.border(BorderStroke(1.dp, Color.Blue)))
        Text("Free breakfast", modifier =  Modifier.border(BorderStroke(1.dp, Color.Blue)))
        Text("Free cancellation", modifier =  Modifier.border(BorderStroke(1.dp, Color.Blue)))
        Text("£50 pn", modifier =  Modifier.border(BorderStroke(1.dp, Color.Blue)))
    }
}