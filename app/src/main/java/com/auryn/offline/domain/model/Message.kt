package com.auryn.offline.domain.model

import java.util.UUID

/**
 * Domain model representing a message in a conversation
 */
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val conversationId: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val voiceEnabled: Boolean = false
)
