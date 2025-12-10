package com.auryn.offline.data.repository

import com.auryn.offline.data.local.ConversationDao
import com.auryn.offline.data.local.entity.toDomainModel
import com.auryn.offline.data.local.entity.toEntity
import com.auryn.offline.domain.model.Conversation
import com.auryn.offline.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of ConversationRepository using Room database
 */
class ConversationRepositoryImpl @Inject constructor(
    private val conversationDao: ConversationDao
) : ConversationRepository {
    
    override fun getAllConversations(): Flow<List<Conversation>> {
        return conversationDao.getAllConversations()
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    override suspend fun getConversation(conversationId: String): Conversation? {
        return conversationDao.getConversation(conversationId)?.toDomainModel()
    }
    
    override suspend fun createConversation(conversation: Conversation) {
        conversationDao.insertConversation(conversation.toEntity())
    }
    
    override suspend fun updateConversation(conversation: Conversation) {
        conversationDao.updateConversation(conversation.toEntity())
    }
    
    override suspend fun deleteConversation(conversationId: String) {
        conversationDao.deleteConversation(conversationId)
    }
}
