package com.auryn.offline.domain.usecase

import com.auryn.offline.domain.model.Conversation
import com.auryn.offline.domain.model.Message
import com.auryn.offline.domain.repository.ConversationRepository
import com.auryn.offline.domain.repository.MessageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.UUID

/**
 * Unit tests for SendMessageUseCase
 */
class SendMessageUseCaseTest {
    
    private lateinit var messageRepository: MessageRepository
    private lateinit var conversationRepository: ConversationRepository
    private lateinit var sendMessageUseCase: SendMessageUseCase
    
    @Before
    fun setup() {
        messageRepository = mockk(relaxed = true)
        conversationRepository = mockk(relaxed = true)
        sendMessageUseCase = SendMessageUseCase(messageRepository, conversationRepository)
    }
    
    @Test
    fun `sendMessage should insert user message and AI response`() = runTest {
        // Arrange
        val conversationId = UUID.randomUUID().toString()
        val userInput = "Hello"
        val conversation = Conversation(
            id = conversationId,
            title = "Test Chat",
            messageCount = 0
        )
        
        coEvery { conversationRepository.getConversation(conversationId) } returns conversation
        
        // Act
        val result = sendMessageUseCase(conversationId, userInput, false)
        
        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 2) { messageRepository.insertMessage(any()) }
        coVerify(exactly = 1) { conversationRepository.updateConversation(any()) }
    }
    
    @Test
    fun `sendMessage should generate appropriate greeting response`() = runTest {
        // Arrange
        val conversationId = UUID.randomUUID().toString()
        val userInput = "hello"
        val conversation = Conversation(id = conversationId, title = "Test")
        
        coEvery { conversationRepository.getConversation(conversationId) } returns conversation
        
        // Act
        val result = sendMessageUseCase(conversationId, userInput, false)
        
        // Assert
        assertTrue(result.isSuccess)
        val aiMessage = result.getOrNull()
        assertNotNull(aiMessage)
        assertTrue(aiMessage?.content?.contains("Hello", ignoreCase = true) == true)
    }
    
    @Test
    fun `sendMessage should update conversation message count`() = runTest {
        // Arrange
        val conversationId = UUID.randomUUID().toString()
        val userInput = "Test message"
        val conversation = Conversation(
            id = conversationId,
            title = "Test",
            messageCount = 5
        )
        
        coEvery { conversationRepository.getConversation(conversationId) } returns conversation
        
        // Act
        sendMessageUseCase(conversationId, userInput, false)
        
        // Assert
        coVerify {
            conversationRepository.updateConversation(
                match { it.messageCount == 7 } // Original 5 + 2 new messages
            )
        }
    }
}
