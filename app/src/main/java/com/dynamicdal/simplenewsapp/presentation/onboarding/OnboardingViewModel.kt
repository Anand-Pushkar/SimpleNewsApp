package com.dynamicdal.simplenewsapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dynamicdal.simplenewsapp.domain.userprefs.MutableUserPrefs
import com.dynamicdal.simplenewsapp.presentation.onboarding.view.OnboardingUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
@Inject
constructor(
    private val userPrefs: MutableUserPrefs
) : ViewModel() {

    fun onUIEvent(event: OnboardingUIEvent) =
        when (event) {
            is OnboardingUIEvent.SaveAppEntry -> saveAppEntry()
        }

    private fun saveAppEntry() {
        viewModelScope.launch {
            userPrefs.saveAppEntry()
        }
    }
}