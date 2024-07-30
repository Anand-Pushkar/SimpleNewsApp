package com.dynamicdal.simplenewsapp.data.network.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleAM>,
)
