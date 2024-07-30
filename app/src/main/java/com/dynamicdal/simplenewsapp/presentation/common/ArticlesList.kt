package com.dynamicdal.simplenewsapp.presentation.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.dynamicdal.simplenewsapp.domain.models.Article
import com.dynamicdal.simplenewsapp.presentation.Dimens.TOP_BAR_HEIGHT
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_16
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_24
import com.dynamicdal.simplenewsapp.presentation.home.isScrolled

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    lazyListState: LazyListState
) {
    val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(top = padding),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(dp_24),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(count = articles.itemCount) {
                val article = articles[it]
                if (article != null) {
                    ArticleCard(article = article, onClick = { onClick(article) })
                }
            }
            item {
                if (articles.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        error != null -> {
            PlaceholderScreen(error)
            false
        }
        else -> true
    }

}

@Composable
private fun ShimmerEffect() {
    Column(
        modifier = Modifier
            .padding(top = TOP_BAR_HEIGHT),
        verticalArrangement = Arrangement.spacedBy(dp_24)
    ) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = dp_16)
            )
        }
    }
}