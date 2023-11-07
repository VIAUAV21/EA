package hu.bme.aut.retrofitnewscomposedemo.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResult(
    @SerialName("articles")
    val articles: List<Article?>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("totalResults")
    val totalResults: Int? = null
)