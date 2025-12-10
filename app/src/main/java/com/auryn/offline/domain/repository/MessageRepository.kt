package com.auryn.offline.domain.repository

import com.auryn.offline.domain.model.Message
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for message operations following clean architecture
 */
interface MessageRepository {
    /**
     * Get all messages for a specific conversation
     */
    fun getMessagesForConversation(conversationId: String): Flow<List<Message>>
    
    /**
     * Insert a new message
     */
    suspend fun insertMessage(message: Message)
    
    /**
     * Delete a message
     */
    suspend fun deleteMessage(messageId: String)
    
    /**
     * Get a single message by ID
     */
    suspend fun getMessage(messageId: String): Message?
    
    /**
     * Delete all messages for a conversation
     */
    suspend fun deleteMessagesForConversation(conversationId: String)
}
