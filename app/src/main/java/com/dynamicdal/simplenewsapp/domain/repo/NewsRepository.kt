package com.dynamicdal.simplenewsapp.domain.repo

import androidx.paging.PagingData
import com.dynamicdal.simplenewsapp.domain.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String,sources: List<String>): Flow<PagingData<Article>>
}