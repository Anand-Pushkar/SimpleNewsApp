package com.dynamicdal.simplenewsapp.presentation.search

import androidx.paging.PagingData
import com.dynamicdal.simplenewsapp.domain.models.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)