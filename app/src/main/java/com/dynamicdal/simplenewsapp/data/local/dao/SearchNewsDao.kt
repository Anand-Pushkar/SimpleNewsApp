package com.dynamicdal.simplenewsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dynamicdal.simplenewsapp.data.local.enitites.SearchArticleEntity

@Dao
interface SearchNewsDao {

    @Upsert
    suspend fun upsertAll(articles: List<SearchArticleEntity>)

    @Upsert
    suspend fun upsert(article: SearchArticleEntity)

    @Query("SELECT * FROM SearchArticleEntity WHERE content like '%' || :query || '%'")
    fun searchPagingSource(query: String, ): PagingSource<Int, SearchArticleEntity>

    @Query("DELETE From SearchArticleEntity")
    suspend fun clearAll()
}