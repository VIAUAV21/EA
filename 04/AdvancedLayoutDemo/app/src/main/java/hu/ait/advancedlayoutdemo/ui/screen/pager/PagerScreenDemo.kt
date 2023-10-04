package hu.ait.advancedlayoutdemo.ui.screen.pager

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.advancedlayoutdemo.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerDemo() {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 5 })


    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Toast.makeText(context,"$page", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            // Each page contains a Text element
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    painter = painterResource(getImage(page)),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Contact profile picture",
                )

                Text(text = "Page ${page + 1}", fontSize = 24.sp)
            }
        }

        PagerIndicator(pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp))


    }
}


fun getImage(page: Int): Int {
    return when(page%3) {
        0 -> R.drawable.mountain1
        1 -> R.drawable.mountain2
        else -> R.drawable.mountain3
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier
) {
    val currentPage by rememberUpdatedState(pagerState.currentPage)

    Row(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(20.dp)

            )
        }

    }
}
