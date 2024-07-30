@file:OptIn(ExperimentalPagingApi::class)

package com.dynamicdal.simplenewsapp.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.dynamicdal.simplenewsapp.data.local.NewsDatabase
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity.Companion.SEARCH_NEWS_KEY
import com.dynamicdal.simplenewsapp.data.local.enitites.SearchArticleEntity
import com.dynamicdal.simplenewsapp.data.mappers.toSearchArticleEntity
import com.dynamicdal.simplenewsapp.data.network.NewsService
import com.dynamicdal.simplenewsapp.data.network.models.ArticleAM
import kotlinx.coroutines.delay
import java.io.IOException

class SearchRemoteMediator(
    private val service: NewsService,
    private val db: NewsDatabase,
    private val sources: List<String>,
    private val searchQuery: String,
) : RemoteMediator<Int, SearchArticleEntity>() {

    private val remoteKeyDao = db.remoteKeyDao
    private val searchNewsDao = db.searchNewsDao
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = getLoadKey(loadType)
            if (loadKey == NO_KEY) return MediatorResult.Success(endOfPaginationReached = true)

            // fetching news
            delay(2000)
            val articles = fetchNews(loadKey)

            // update db
            updateDb(loadType, articles, loadKey)

            // return result
            MediatorResult.Success(endOfPaginationReached = articles.size < state.config.pageSize)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getLoadKey(
        loadType: LoadType,
    ): Int {
        return when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> NO_KEY
            LoadType.APPEND -> {
                val remoteKey = db.withTransaction {
                    remoteKeyDao.getKey(SEARCH_NEWS_KEY)
                }

                if (remoteKey == null) return 1

                if (remoteKey.nextPage == null) return NO_KEY

                remoteKey.nextPage
            }
        }
    }

    private suspend fun fetchNews(
        loadKey: Int
    ): List<SearchArticleEntity> =
        fetchNewsFromRemote(loadKey).map { it.toSearchArticleEntity() }

    private suspend fun fetchNewsFromRemote(
        loadKey: Int
    ): List<ArticleAM> =
        service.searchNews(
            searchQuery = searchQuery,
            sources = sources.joinToString(separator = ","),
            page = loadKey,
        ).articles

    private suspend fun updateDb(
        loadType: LoadType,
        articles: List<SearchArticleEntity>,
        loadKey: Int
    ) {
        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                searchNewsDao.clearAll()
            }

            // update remote key
            remoteKeyDao.insertKey(
                RemoteKeyEntity(
                    id = SEARCH_NEWS_KEY,
                    nextPage = loadKey + 1
                )
            )

            // update search news
            searchNewsDao.upsertAll(articles)
        }
    }

    companion object {
        const val NO_KEY = 0
    }
}