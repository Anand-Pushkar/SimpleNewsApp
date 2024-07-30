package com.dynamicdal.simplenewsapp.data.network.models

data class ArticleAM(
    val url: String,
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceAM,
    val title: String,
    val urlToImage: String
)
