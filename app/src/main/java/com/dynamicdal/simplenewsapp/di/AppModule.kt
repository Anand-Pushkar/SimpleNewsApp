package com.dynamicdal.simplenewsapp.di

import android.content.Context
import androidx.room.Room
import com.dynamicdal.simplenewsapp.data.repo.NewsRepositoryImpl
import com.dynamicdal.simplenewsapp.data.local.NewsDatabase
import com.dynamicdal.simplenewsapp.data.network.NewsService
import com.dynamicdal.simplenewsapp.data.local.NewsTypeConvertor
import com.dynamicdal.simplenewsapp.data.userprefs.UserPrefsImpl
import com.dynamicdal.simplenewsapp.domain.repo.NewsRepository
import com.dynamicdal.simplenewsapp.domain.userprefs.MutableUserPrefs
import com.dynamicdal.simplenewsapp.domain.userprefs.UserPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPrefs(userPrefsImpl: UserPrefsImpl): UserPrefs = userPrefsImpl

    @Provides
    @Singleton
    fun provideMutableUserPrefs(userPrefsImpl: UserPrefsImpl): MutableUserPrefs = userPrefsImpl

    // db
    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context,
    ): NewsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = NewsDatabase::class.java,
            name = "news.db"
        ).addTypeConverter(NewsTypeConvertor())
            .build()

    // api
    @Provides
    @Singleton
    fun provideNewsApi(): NewsService =
        Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository = newsRepositoryImpl
}