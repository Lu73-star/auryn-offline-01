package com.auryn.offline.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data transfer object for OpenAI chat completion response
 */
data class ChatCompletionResponse(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("object")
    val objectType: String,
    
    @SerializedName("created")
    val created: Long,
    
    @SerializedName("model")
    val model: String,
    
    @SerializedName("choices")
    val choices: List<Choice>,
    
    @SerializedName("usage")
    val usage: Usage?
)

data class Choice(
    @SerializedName("index")
    val index: Int,
    
    @SerializedName("message")
    val message: ChatMessageResponse,
    
    @SerializedName("finish_reason")
    val finishReason: String?
)

data class ChatMessageResponse(
    @SerializedName("role")
    val role: String,
    
    @SerializedName("content")
    val content: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    
    @SerializedName("total_tokens")
    val totalTokens: Int
)
