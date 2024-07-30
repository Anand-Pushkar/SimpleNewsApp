package com.dynamicdal.simplenewsapp.data.mappers

import com.dynamicdal.simplenewsapp.data.local.enitites.SearchArticleEntity
import com.dynamicdal.simplenewsapp.data.network.models.ArticleAM
import com.dynamicdal.simplenewsapp.domain.models.Article

fun ArticleAM.toSearchArticleEntity(): SearchArticleEntity =
    SearchArticleEntity(
        url = url,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.toSourceEntity(),
        title = title,
        urlToImage = urlToImage,
    )

fun SearchArticleEntity.toArticle(): Article =
    Article(
        url = url,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.toSource(),
        title = title,
        urlToImage = urlToImage
    )