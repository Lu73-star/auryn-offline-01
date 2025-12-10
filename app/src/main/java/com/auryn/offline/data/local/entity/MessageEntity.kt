package com.auryn.offline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.auryn.offline.domain.model.Message

/**
 * Room entity for storing messages locally
 */
@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val conversationId: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long,
    val voiceEnabled: Boolean
)

fun MessageEntity.toDomainModel(): Message {
    return Message(
        id = id,
        conversationId = conversationId,
        content = content,
        isFromUser = isFromUser,
        timestamp = timestamp,
        voiceEnabled = voiceEnabled
    )
}

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = id,
        conversationId = conversationId,
        content = content,
        isFromUser = isFromUser,
        timestamp = timestamp,
        voiceEnabled = voiceEnabled
    )
}
