package com.dynamicdal.simplenewsapp.presentation.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.dynamicdal.simplenewsapp.domain.models.Article
import com.dynamicdal.simplenewsapp.presentation.Dimens.TOP_BAR_HEIGHT
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_16
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_24
import com.dynamicdal.simplenewsapp.presentation.common.ArticlesList
import com.dynamicdal.simplenewsapp.presentation.common.SearchBar
import com.dynamicdal.simplenewsapp.presentation.home.isScrolled

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToDetails: (Article) -> Unit
) {
    SearchView(
        state = viewModel.state.value,
        event = viewModel::onEvent,
        navigateToDetails = navigateToDetails
    )
}

@Composable
fun SearchView(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dp_24)
            .statusBarsPadding()
    ) {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = dp_16)
                .animateContentSize(animationSpec = tween(durationMillis = 300))
                .height(if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) })

        Spacer(modifier = Modifier.height(dp_24))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = { navigateToDetails(it) },
                lazyListState = lazyListState
            )
        }
    }
}