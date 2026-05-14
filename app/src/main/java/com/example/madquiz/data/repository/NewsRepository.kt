package com.example.madquiz.data.repository

import com.example.madquiz.data.api.RetrofitClient
import com.example.madquiz.data.model.Article

class NewsRepository {
    suspend fun getHeadlines(country: String, apiKey: String): Result<List<Article>> {
        return try {
            val response = RetrofitClient.api.getTopHeadlines(
                country = country,
                apiKey  = apiKey
            )
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
