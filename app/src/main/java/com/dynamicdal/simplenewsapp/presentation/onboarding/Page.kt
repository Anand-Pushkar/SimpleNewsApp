package com.dynamicdal.simplenewsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.dynamicdal.simplenewsapp.R

data class Page(
    val title: String,
    @DrawableRes val image: Int
)


val pages = listOf(
    Page(
        title = "Get the latest news",
        image = R.drawable.ic_launcher_background
    ),
    Page(
        title = "Search for the news you want to see",
        image = R.drawable.ic_launcher_background
    )
)


