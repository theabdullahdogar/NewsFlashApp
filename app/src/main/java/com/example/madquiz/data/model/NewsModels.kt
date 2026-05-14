package com.example.madquiz.data.model

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
)

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val source: Source
)

data class Source(
    val name: String,
    val url: String
)

data class Country(val code: String, val name: String, val flag: String)

val countryList = listOf(
    Country("pk", "Pakistan",      "🇵🇰"),
    Country("us", "United States", "🇺🇸"),
    Country("gb", "United Kingdom","🇬🇧"),
    Country("in", "India",         "🇮🇳"),
    Country("sa", "Saudi Arabia",  "🇸🇦"),
    Country("ae", "UAE",           "🇦🇪")
)
