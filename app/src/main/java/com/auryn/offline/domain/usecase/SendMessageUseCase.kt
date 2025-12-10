package com.auryn.offline.domain.usecase

import com.auryn.offline.domain.model.Message
import com.auryn.offline.domain.repository.ConversationRepository
import com.auryn.offline.domain.repository.MessageRepository
import javax.inject.Inject

/**
 * Use case for sending a message and generating AI response
 */
class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(conversationId: String, content: String, voiceEnabled: Boolean = false): Result<Message> {
        return try {
            // Create and save user message
            val userMessage = Message(
                conversationId = conversationId,
                content = content,
                isFromUser = true,
                voiceEnabled = voiceEnabled
            )
            messageRepository.insertMessage(userMessage)
            
            // Generate AI response (placeholder for offline AI model)
            val aiResponse = generateAIResponse(content)
            val aiMessage = Message(
                conversationId = conversationId,
                content = aiResponse,
                isFromUser = false,
                voiceEnabled = voiceEnabled
            )
            messageRepository.insertMessage(aiMessage)
            
            // Update conversation timestamp
            val conversation = conversationRepository.getConversation(conversationId)
            conversation?.let {
                conversationRepository.updateConversation(
                    it.copy(
                        updatedAt = System.currentTimeMillis(),
                        messageCount = it.messageCount + 2
                    )
                )
            }
            
            Result.success(aiMessage)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun generateAIResponse(userInput: String): String {
        // Placeholder for offline AI model integration
        // In the future, this will integrate with local ML models
        return when {
            userInput.contains("hello", ignoreCase = true) || 
            userInput.contains("hi", ignoreCase = true) -> 
                "Hello! I'm Auryn, your offline AI assistant. How can I help you today?"
            
            userInput.contains("help", ignoreCase = true) -> 
                "I'm here to assist you! I can help with conversations, answer questions, and more. All processing is done locally on your device for privacy."
            
            userInput.contains("weather", ignoreCase = true) -> 
                "I'm currently running in offline mode and cannot access real-time weather data. In a future update, you'll be able to enable online features for live information."
            
            else -> 
                "I understand you said: '$userInput'. As an offline AI, I'm continuously learning to provide better responses. This feature will be enhanced in future updates."
        }
    }
}
