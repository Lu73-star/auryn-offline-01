package com.auryn.offline.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.auryn.offline.data.local.entity.ConversationEntity
import com.auryn.offline.data.local.entity.MessageEntity

/**
 * Room database for local persistent storage
 */
@Database(
    entities = [MessageEntity::class, ConversationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AurynDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun conversationDao(): ConversationDao
    
    companion object {
        const val DATABASE_NAME = "auryn_database"
    }
}
