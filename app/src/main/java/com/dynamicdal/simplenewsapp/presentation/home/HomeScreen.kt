package com.dynamicdal.simplenewsapp.presentation.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dynamicdal.simplenewsapp.domain.models.Article
import com.dynamicdal.simplenewsapp.presentation.Dimens.TOP_BAR_HEIGHT
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_16
import com.dynamicdal.simplenewsapp.presentation.Dimens.dp_24
import com.dynamicdal.simplenewsapp.presentation.common.ArticlesList
import com.dynamicdal.simplenewsapp.presentation.common.SearchBar
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    val articles = viewModel.newsPagingFlow.collectAsLazyPagingItems()
    val state = viewModel.state.value
    HomeView(
        state = state,
        articles = articles,
        navigateToSearch = navigateToSearch,
        navigateToDetails = navigateToDetails,
        event = viewModel::onEvent
    )
}

@Composable
fun HomeView(
    state: HomeState,
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit,
    event: (HomeEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = state.maxScrollingValue) {
        delay(500)
        if (state.maxScrollingValue > 0) {
            scrollState.animateScrollTo(
                value = state.maxScrollingValue,
                animationSpec = infiniteRepeatable(
                    tween(
                        durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                        easing = LinearEasing,
                        delayMillis = 1000
                    )
                )
            )
        }
    }
    // save the maxScrollingValue
    LaunchedEffect(key1 = scrollState.maxValue) {
        event(HomeEvent.UpdateMaxScrollingValue(scrollState.maxValue))
    }
    // Save scroll position
    LaunchedEffect(key1 = scrollState.value) {
        event(HomeEvent.UpdateScrollValue(scrollState.value))
    }

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
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {}
        )
        ArticlesList(
            modifier = Modifier.padding(horizontal = dp_16),
            articles = articles,
            onClick = {
                navigateToDetails(it)
            },
            lazyListState = lazyListState
        )
    }
}

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemScrollOffset > 0

