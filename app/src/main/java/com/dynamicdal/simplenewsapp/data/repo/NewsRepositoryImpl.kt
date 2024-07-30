@file:OptIn(ExperimentalPagingApi::class)

package com.dynamicdal.simplenewsapp.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.dynamicdal.simplenewsapp.data.local.NewsDatabase
import com.dynamicdal.simplenewsapp.data.mappers.toArticle
import com.dynamicdal.simplenewsapp.data.network.NewsService
import com.dynamicdal.simplenewsapp.data.mediators.NewsRemoteMediator
import com.dynamicdal.simplenewsapp.data.mediators.SearchRemoteMediator
import com.dynamicdal.simplenewsapp.domain.models.Article
import com.dynamicdal.simplenewsapp.domain.repo.NewsRepository
import com.dynamicdal.simplenewsapp.util.Constants.PAGE_SIZE
import com.dynamicdal.simplenewsapp.util.Constants.SOURCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl
@Inject
constructor(
    private val newsService: NewsService,
    private val newDb: NewsDatabase,
) : NewsRepository {
    override fun getNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = NewsRemoteMediator(newsService, newDb, SOURCES),
            pagingSourceFactory = { newDb.newsDao.newsPagingSource() }
        )
            .flow.map { pagingData ->
                pagingData.map { it.toArticle() }
            }
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            remoteMediator = SearchRemoteMediator(newsService, newDb, SOURCES, searchQuery),
            pagingSourceFactory = { newDb.searchNewsDao.searchPagingSource(searchQuery) }
        )
            .flow.map { pagingData ->
                pagingData.map { it.toArticle() }
            }
    }
}