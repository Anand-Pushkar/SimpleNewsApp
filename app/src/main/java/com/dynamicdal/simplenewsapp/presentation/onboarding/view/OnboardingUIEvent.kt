package com.dynamicdal.simplenewsapp.presentation.onboarding.view

sealed class OnboardingUIEvent {
    data object SaveAppEntry: OnboardingUIEvent()
}