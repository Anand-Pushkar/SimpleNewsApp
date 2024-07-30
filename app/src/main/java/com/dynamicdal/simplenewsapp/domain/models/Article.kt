package com.dynamicdal.simplenewsapp.domain.models

data class Article(
    val url: String,
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val urlToImage: String
)
