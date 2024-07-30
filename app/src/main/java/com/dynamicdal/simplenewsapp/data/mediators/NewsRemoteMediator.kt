@file:OptIn(ExperimentalPagingApi::class)

package com.dynamicdal.simplenewsapp.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.dynamicdal.simplenewsapp.data.local.enitites.ArticleEntity
import com.dynamicdal.simplenewsapp.data.local.NewsDatabase
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity.Companion.NEWS_KEY
import com.dynamicdal.simplenewsapp.data.mappers.toArticleEntity
import com.dynamicdal.simplenewsapp.data.network.NewsService
import com.dynamicdal.simplenewsapp.data.network.models.ArticleAM
import kotlinx.coroutines.delay
import java.io.IOException

class NewsRemoteMediator(
    private val service: NewsService,
    private val db: NewsDatabase,
    private val sources: List<String>,
) : RemoteMediator<Int, ArticleEntity>() {

    private val remoteKeyDao = db.remoteKeyDao
    private val newsDao = db.newsDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = getLoadKey(loadType)
            if (loadKey == NO_KEY) return MediatorResult.Success(endOfPaginationReached = true)

            // fetch news
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
                    remoteKeyDao.getKey(NEWS_KEY)
                }

                if (remoteKey == null) return 1

                if (remoteKey.nextPage == null) return NO_KEY

                remoteKey.nextPage
            }
        }
    }


    private suspend fun fetchNews(
        loadKey: Int
    ): List<ArticleEntity> =
        fetchNewsFromRemote(loadKey).map { it.toArticleEntity() }

    private suspend fun fetchNewsFromRemote(
        loadKey: Int
    ): List<ArticleAM> =
        service.getNews(
            page = loadKey,
            sources = sources.joinToString(separator = ","),
        ).articles

    private suspend fun updateDb(
        loadType: LoadType,
        articles: List<ArticleEntity>,
        loadKey: Int
    ) {
        db.withTransaction {
            if(loadType == LoadType.REFRESH) {
                newsDao.clearAll()
            }

            // update remote key
            remoteKeyDao.insertKey(
                RemoteKeyEntity(
                    id = NEWS_KEY,
                    nextPage = loadKey + 1
                )
            )

            // update news
            newsDao.upsertAll(articles)
        }
    }

    companion object {
        const val NO_KEY = 0
    }
}