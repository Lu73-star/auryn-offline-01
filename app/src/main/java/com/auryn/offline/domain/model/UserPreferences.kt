package com.auryn.offline.domain.model

/**
 * Domain model for user preferences and settings
 */
data class UserPreferences(
    val voiceEnabled: Boolean = false,
    val offlineMode: Boolean = true,
    val tierLevel: TierLevel = TierLevel.FREE,
    val theme: ThemeMode = ThemeMode.SYSTEM
)

enum class TierLevel {
    FREE,
    PRO,
    ULTRA
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}
