package hu.bme.aut.retrofitnewscomposedemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.retrofitnewscomposedemo.data.Article
import hu.bme.aut.retrofitnewscomposedemo.data.NewsResult

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Button(onClick = {
            newsViewModel.getNews()
        }) {
            Text(text = "Refresh")
        }


        when (newsViewModel.newsUiState) {
            is NewsUiState.Init -> {}
            is NewsUiState.Loading -> CircularProgressIndicator()
            is NewsUiState.Success -> ResultScreen((newsViewModel.newsUiState as NewsUiState.Success).news)
            is NewsUiState.Error -> Text(text = "Error: ${(newsViewModel.newsUiState as NewsUiState.Error).errorMsg}")
        }
    }
}

@Composable
fun ResultScreenString(news: String) {
    Text(text = news)
}

@Composable
fun ResultScreen(newsResult: NewsResult) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(newsResult.articles!!) {
            NewsCard(it!!)
        }
    }
}



@Composable
fun NewsCard (
    article: Article
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        //append(article.url)
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue)) {
            append("Link")
            addStringAnnotation(
                tag = "URL",
                annotation = article.url!!,
                start = length - article.url!!.length,
                end = length
            )
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = article.author ?: "",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = article.publishedAt ?: "",
                fontSize = 12.sp
            )
            Text(
                text = article.title ?: ""
            )
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                }
            )
        }
    }
}