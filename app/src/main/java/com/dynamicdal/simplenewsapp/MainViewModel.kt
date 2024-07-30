package com.dynamicdal.simplenewsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamicdal.simplenewsapp.domain.userprefs.UserPrefs
import com.dynamicdal.simplenewsapp.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val userPrefs: UserPrefs,
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        userPrefs.getAppEntry().onEach { startFromHome ->
            startDestination = if (startFromHome) {
                Route.NewsNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }

}