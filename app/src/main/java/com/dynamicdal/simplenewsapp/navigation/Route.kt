package com.dynamicdal.simplenewsapp.navigation

import android.telecom.Call.Details

sealed class Route(
    val route: String
) {
    // screens
    object OnboardingScreen : Route(ONBOARDING_SCREEN)
    object HomeScreen : Route(HOME_SCREEN)
    object SearchScreen : Route(SEARCH_SCREEN)
    object DetailsScreen : Route(DETAILS_SCREEN)

    // sub navigations
    object AppStartNavigation : Route(APP_START_NAVIGATION)
    object NewsNavigation : Route(NEWS_NAVIGATION)

    companion object {
        private const val ONBOARDING_SCREEN = "onboardingScreen"
        private const val HOME_SCREEN = "homeScreen"
        private const val SEARCH_SCREEN = "searchScreen"
        private const val DETAILS_SCREEN = "detailsScreen"

        private const val APP_START_NAVIGATION = "appStartNavigation"
        const val NEWS_NAVIGATION = "newsNavigation"
    }
}