package com.dynamicdal.simplenewsapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dynamicdal.simplenewsapp.R
import com.dynamicdal.simplenewsapp.domain.models.Article
import com.dynamicdal.simplenewsapp.navigation.component.BottomNavigationItem
import com.dynamicdal.simplenewsapp.navigation.component.NewsBottomNavigation
import com.dynamicdal.simplenewsapp.presentation.details.DetailsScreen
import com.dynamicdal.simplenewsapp.presentation.home.HomeScreen
import com.dynamicdal.simplenewsapp.presentation.home.HomeViewModel
import com.dynamicdal.simplenewsapp.presentation.search.SearchScreen
import com.dynamicdal.simplenewsapp.presentation.search.SearchViewModel
import com.dynamicdal.simplenewsapp.util.Constants.ARTICLE_CONTENT
import com.dynamicdal.simplenewsapp.util.Constants.ARTICLE_IMAGE_URL
import com.dynamicdal.simplenewsapp.util.Constants.ARTICLE_TITLE
import com.dynamicdal.simplenewsapp.util.Constants.ARTICLE_URL

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            else -> 0
        }

    }


    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    },
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    viewModel = viewModel,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.DetailsScreen.route) {
                val title = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(ARTICLE_TITLE)
                val content = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(ARTICLE_CONTENT)
                val url = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(ARTICLE_URL)
                val imageUrl = navController.previousBackStackEntry?.savedStateHandle?.get<String?>(ARTICLE_IMAGE_URL)

                if (title != null && content != null) {
                    DetailsScreen(
                        newsUrl = url,
                        imageUrl = imageUrl,
                        title = title,
                        content = content,
                        navigateUp = { navController.navigateUp() })
                }
            }
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    saveArticleInStateHandle(navController, article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}

private fun saveArticleInStateHandle(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set(ARTICLE_TITLE, article.title)
    navController.currentBackStackEntry?.savedStateHandle?.set(ARTICLE_CONTENT, article.content)
    navController.currentBackStackEntry?.savedStateHandle?.set(ARTICLE_URL, article.url)
    navController.currentBackStackEntry?.savedStateHandle?.set(ARTICLE_IMAGE_URL, article.urlToImage)
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}