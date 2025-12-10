package com.auryn.offline.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data transfer object for OpenAI chat completion request
 */
data class ChatCompletionRequest(
    @SerializedName("model")
    val model: String = "gpt-3.5-turbo",
    
    @SerializedName("messages")
    val messages: List<ChatMessage>,
    
    @SerializedName("temperature")
    val temperature: Double = 0.7,
    
    @SerializedName("max_tokens")
    val maxTokens: Int? = null
)

data class ChatMessage(
    @SerializedName("role")
    val role: String, // "system", "user", or "assistant"
    
    @SerializedName("content")
    val content: String
)
