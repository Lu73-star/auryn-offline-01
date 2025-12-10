package com.auryn.offline.domain.repository

import com.auryn.offline.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for conversation operations
 */
interface ConversationRepository {
    /**
     * Get all conversations
     */
    fun getAllConversations(): Flow<List<Conversation>>
    
    /**
     * Get a specific conversation by ID
     */
    suspend fun getConversation(conversationId: String): Conversation?
    
    /**
     * Create a new conversation
     */
    suspend fun createConversation(conversation: Conversation)
    
    /**
     * Update an existing conversation
     */
    suspend fun updateConversation(conversation: Conversation)
    
    /**
     * Delete a conversation
     */
    suspend fun deleteConversation(conversationId: String)
}
