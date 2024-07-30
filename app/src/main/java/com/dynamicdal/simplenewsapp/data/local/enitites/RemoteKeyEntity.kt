package com.dynamicdal.simplenewsapp.data.local.enitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val nextPage: Int?
) {
    companion object {
        const val NEWS_KEY = "news.key"
        const val SEARCH_NEWS_KEY = "search.news.key"
    }
}
