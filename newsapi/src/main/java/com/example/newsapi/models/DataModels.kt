package com.example.newsapi.models

data class NewsList(
    val status: String?,
    val articles: List<Article>
)

data class Article (
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)