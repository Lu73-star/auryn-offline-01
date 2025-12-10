package com.auryn.offline.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.auryn.offline.domain.model.ThemeMode
import com.auryn.offline.domain.model.TierLevel
import com.auryn.offline.domain.model.UserPreferences
import com.auryn.offline.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of PreferencesRepository using DataStore
 */
class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {
    
    private object PreferencesKeys {
        val VOICE_ENABLED = booleanPreferencesKey("voice_enabled")
        val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        val TIER_LEVEL = stringPreferencesKey("tier_level")
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }
    
    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStore.data.map { preferences ->
            UserPreferences(
                voiceEnabled = preferences[PreferencesKeys.VOICE_ENABLED] ?: false,
                offlineMode = preferences[PreferencesKeys.OFFLINE_MODE] ?: true,
                tierLevel = TierLevel.valueOf(
                    preferences[PreferencesKeys.TIER_LEVEL] ?: TierLevel.FREE.name
                ),
                theme = ThemeMode.valueOf(
                    preferences[PreferencesKeys.THEME_MODE] ?: ThemeMode.SYSTEM.name
                )
            )
        }
    }
    
    override suspend fun updatePreferences(preferences: UserPreferences) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.VOICE_ENABLED] = preferences.voiceEnabled
            mutablePreferences[PreferencesKeys.OFFLINE_MODE] = preferences.offlineMode
            mutablePreferences[PreferencesKeys.TIER_LEVEL] = preferences.tierLevel.name
            mutablePreferences[PreferencesKeys.THEME_MODE] = preferences.theme.name
        }
    }
    
    override suspend fun toggleVoiceMode(enabled: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.VOICE_ENABLED] = enabled
        }
    }
    
    override suspend fun toggleOfflineMode(enabled: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.OFFLINE_MODE] = enabled
        }
    }
}
