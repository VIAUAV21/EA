package hu.bme.aut.retrofitnewscomposedemo.network

import hu.bme.aut.retrofitnewscomposedemo.data.NewsResult
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=hu&apiKey=e670040e6e2c42988d4725bf2413afb4

// Host: https://newsapi.org
//
// Path: /v2/top-headlines
//
// Query params: ?country=hu&apiKey=e670040e6e2c42988d4725bf2413afb4


interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country") country: String,
                              @Query("apiKey") apiKey: String): NewsResult

    @GET("/v2/top-headlines")
    suspend fun getNewsAsString(@Query("country") country: String,
                                @Query("apiKey") apiKey: String): String

}
