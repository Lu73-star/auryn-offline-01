package com.auryn.offline.domain.repository

import com.auryn.offline.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user preferences
 */
interface PreferencesRepository {
    /**
     * Get user preferences as a Flow
     */
    fun getUserPreferences(): Flow<UserPreferences>
    
    /**
     * Update user preferences
     */
    suspend fun updatePreferences(preferences: UserPreferences)
    
    /**
     * Toggle voice mode
     */
    suspend fun toggleVoiceMode(enabled: Boolean)
    
    /**
     * Toggle offline mode
     */
    suspend fun toggleOfflineMode(enabled: Boolean)
}
