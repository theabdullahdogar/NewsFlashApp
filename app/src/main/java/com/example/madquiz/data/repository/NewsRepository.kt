package com.example.madquiz.data.repository

import com.example.madquiz.data.api.RetrofitClient
import com.example.madquiz.data.model.Article
import com.example.madquiz.data.model.countryNames

class NewsRepository {
    suspend fun getHeadlines(country: String, apiKey: String): Result<List<Article>> {
        return try {
            val response = RetrofitClient.api.getTopHeadlines(country = country, apiKey = apiKey)
            if (response.articles.isNotEmpty()) {
                Result.success(response.articles)
            } else {
                // Fallback: search by country name when top-headlines returns empty
                val query = countryNames[country] ?: country
                val searchResponse = RetrofitClient.api.searchNews(query = query, apiKey = apiKey)
                Result.success(searchResponse.articles)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
