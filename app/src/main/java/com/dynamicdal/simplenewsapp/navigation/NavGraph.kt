package com.dynamicdal.simplenewsapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dynamicdal.simplenewsapp.R
import com.dynamicdal.simplenewsapp.presentation.home.HomeScreen
import com.dynamicdal.simplenewsapp.presentation.home.HomeViewModel
import com.dynamicdal.simplenewsapp.presentation.onboarding.OnboardingViewModel
import com.dynamicdal.simplenewsapp.presentation.onboarding.view.OnboardingScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnboardingScreen.route
        ) {
            Log.d("NewsTag", "NavGraph: AppStartNavigation ----->")
            composable(
                route = Route.OnboardingScreen.route
            ) {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(viewModel)
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.HomeScreen.route
        ) {
            Log.d("NewsTag", "NavGraph: NewsNavigation ----->")
            composable(
                route = Route.HomeScreen.route
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NewsNavigator()
                }
            }
        }
    }

}