package com.auryn.offline.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auryn.offline.domain.model.Conversation
import com.auryn.offline.domain.model.Message
import com.auryn.offline.domain.repository.ConversationRepository
import com.auryn.offline.domain.repository.PreferencesRepository
import com.auryn.offline.domain.usecase.GetConversationMessagesUseCase
import com.auryn.offline.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel for the chat screen
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getConversationMessagesUseCase: GetConversationMessagesUseCase,
    private val conversationRepository: ConversationRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    
    private var currentConversationId: String? = null
    
    init {
        loadOrCreateConversation()
        observePreferences()
    }
    
    private fun loadOrCreateConversation() {
        viewModelScope.launch {
            val conversations = conversationRepository.getAllConversations().first()
            if (conversations.isEmpty()) {
                // Create initial conversation
                val newConversation = Conversation(
                    title = "New Chat",
                    createdAt = System.currentTimeMillis()
                )
                conversationRepository.createConversation(newConversation)
                currentConversationId = newConversation.id
            } else {
                currentConversationId = conversations.first().id
            }
            
            currentConversationId?.let { id ->
                observeMessages(id)
            }
        }
    }
    
    private fun observeMessages(conversationId: String) {
        viewModelScope.launch {
            getConversationMessagesUseCase(conversationId).collect { messages ->
                _uiState.value = _uiState.value.copy(
                    messages = messages,
                    isLoading = false
                )
            }
        }
    }
    
    private fun observePreferences() {
        viewModelScope.launch {
            preferencesRepository.getUserPreferences().collect { preferences ->
                _uiState.value = _uiState.value.copy(
                    voiceEnabled = preferences.voiceEnabled,
                    offlineMode = preferences.offlineMode
                )
            }
        }
    }
    
    fun sendMessage(content: String) {
        if (content.isBlank()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                inputText = ""
            )
            
            currentConversationId?.let { conversationId ->
                val result = sendMessageUseCase(
                    conversationId = conversationId,
                    content = content,
                    voiceEnabled = _uiState.value.voiceEnabled
                )
                
                if (result.isFailure) {
                    _uiState.value = _uiState.value.copy(
                        error = result.exceptionOrNull()?.message ?: "Failed to send message",
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
    
    fun updateInputText(text: String) {
        _uiState.value = _uiState.value.copy(inputText = text)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun createNewConversation() {
        viewModelScope.launch {
            val newConversation = Conversation(
                title = "New Chat",
                createdAt = System.currentTimeMillis()
            )
            conversationRepository.createConversation(newConversation)
            currentConversationId = newConversation.id
            observeMessages(newConversation.id)
        }
    }
}

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val voiceEnabled: Boolean = false,
    val offlineMode: Boolean = true
)
