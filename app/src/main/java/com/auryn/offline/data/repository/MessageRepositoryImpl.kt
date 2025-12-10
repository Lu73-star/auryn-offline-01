package com.auryn.offline.data.repository

import com.auryn.offline.data.local.MessageDao
import com.auryn.offline.data.local.entity.toDomainModel
import com.auryn.offline.data.local.entity.toEntity
import com.auryn.offline.domain.model.Message
import com.auryn.offline.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of MessageRepository using Room database
 */
class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageRepository {
    
    override fun getMessagesForConversation(conversationId: String): Flow<List<Message>> {
        return messageDao.getMessagesForConversation(conversationId)
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    override suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message.toEntity())
    }
    
    override suspend fun deleteMessage(messageId: String) {
        messageDao.deleteMessage(messageId)
    }
    
    override suspend fun getMessage(messageId: String): Message? {
        return messageDao.getMessage(messageId)?.toDomainModel()
    }
    
    override suspend fun deleteMessagesForConversation(conversationId: String) {
        messageDao.deleteMessagesForConversation(conversationId)
    }
}
