package com.auryn.offline.domain.usecase

import com.auryn.offline.domain.model.Message
import com.auryn.offline.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving messages for a conversation
 */
class GetConversationMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(conversationId: String): Flow<List<Message>> {
        return messageRepository.getMessagesForConversation(conversationId)
    }
}
