package com.dynamicdal.simplenewsapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dynamicdal.simplenewsapp.data.local.enitites.ArticleEntity

@Dao
interface NewsDao {

    @Upsert
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Upsert
    suspend fun upsert(article: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity")
    fun newsPagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE From ArticleEntity")
    suspend fun clearAll()
}