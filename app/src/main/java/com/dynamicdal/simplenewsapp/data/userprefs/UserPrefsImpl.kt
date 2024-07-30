package com.dynamicdal.simplenewsapp.data.userprefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dynamicdal.simplenewsapp.domain.userprefs.MutableUserPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPrefsImpl
@Inject
constructor(
    @ApplicationContext private val context: Context
) : MutableUserPrefs {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS_KEY)

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[booleanPreferencesKey(APP_ENTRY_KEY)] = true
        }
    }

    override fun getAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(APP_ENTRY_KEY)] ?: false
        }
    }

    companion object {
        private const val USER_SETTINGS_KEY = "user_settings_key"
        private const val APP_ENTRY_KEY = "app_entry_key"
    }
}