package com.auryn.offline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.auryn.offline.domain.model.Conversation

/**
 * Room entity for storing conversations locally
 */
@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long,
    val messageCount: Int
)

fun ConversationEntity.toDomainModel(): Conversation {
    return Conversation(
        id = id,
        title = title,
        createdAt = createdAt,
        updatedAt = updatedAt,
        messageCount = messageCount
    )
}

fun Conversation.toEntity(): ConversationEntity {
    return ConversationEntity(
        id = id,
        title = title,
        createdAt = createdAt,
        updatedAt = updatedAt,
        messageCount = messageCount
    )
}
