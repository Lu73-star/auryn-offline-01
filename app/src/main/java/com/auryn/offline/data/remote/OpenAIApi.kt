package com.auryn.offline.data.remote

import com.auryn.offline.data.remote.dto.ChatCompletionRequest
import com.auryn.offline.data.remote.dto.ChatCompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * OpenAI API interface for future online integration
 * Currently not used in offline mode
 */
interface OpenAIApi {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}
