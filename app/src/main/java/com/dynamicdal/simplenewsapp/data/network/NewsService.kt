package com.dynamicdal.simplenewsapp.data.network

import com.dynamicdal.simplenewsapp.BuildConfig
import com.dynamicdal.simplenewsapp.data.network.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}