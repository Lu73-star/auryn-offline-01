package com.auryn.offline.domain.model

import java.util.UUID

/**
 * Domain model representing a conversation thread
 */
data class Conversation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val messageCount: Int = 0
)
