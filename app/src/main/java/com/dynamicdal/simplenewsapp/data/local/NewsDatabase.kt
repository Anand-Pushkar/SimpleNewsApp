package com.dynamicdal.simplenewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dynamicdal.simplenewsapp.data.local.dao.NewsDao
import com.dynamicdal.simplenewsapp.data.local.dao.RemoteKeyDao
import com.dynamicdal.simplenewsapp.data.local.dao.SearchNewsDao
import com.dynamicdal.simplenewsapp.data.local.enitites.ArticleEntity
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity
import com.dynamicdal.simplenewsapp.data.local.enitites.SearchArticleEntity
import com.dynamicdal.simplenewsapp.data.local.enitites.SourceEntity

@Database(
    entities = [
        ArticleEntity::class,
        SearchArticleEntity::class,
        SourceEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1
)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsDao
    abstract val searchNewsDao: SearchNewsDao
    abstract val remoteKeyDao: RemoteKeyDao
}