package com.dynamicdal.simplenewsapp.data.local.enitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey val url: String,
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceEntity,
    val title: String,
    val urlToImage: String,
)
