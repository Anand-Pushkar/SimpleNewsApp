package com.dynamicdal.simplenewsapp.domain.userprefs

import kotlinx.coroutines.flow.Flow

// For read only access
interface UserPrefs {
    fun getAppEntry(): Flow<Boolean>
}

// For read and write access
interface MutableUserPrefs : UserPrefs {
    suspend fun saveAppEntry()
}