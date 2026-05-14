package com.example.madquiz.data.api

import com.example.madquiz.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("lang")     lang: String     = "en",
        @Query("country")  country: String  = "us",
        @Query("max")      max: Int         = 10,
        @Query("apikey")   apiKey: String
    ): NewsResponse

    @GET("search")
    suspend fun searchNews(
        @Query("q")      query: String,
        @Query("lang")   lang: String = "en",
        @Query("max")    max: Int     = 10,
        @Query("apikey") apiKey: String
    ): NewsResponse
}
